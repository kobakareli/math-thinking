package tmand13.math_thinking;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.udojava.evalex.Expression;

import java.math.BigDecimal;

import tmand13.math_thinking.db.AppDatabase;
import tmand13.math_thinking.db.Task;
import tmand13.math_thinking.db.TaskDao;

// TODO remove unused libraries and solve all intellij idea
// advice
public class TaskFragment extends Fragment {
    private static final double EPS = 1e-8;

    Integer taskId;
    //TODO control edittext based on answer type
    Task task;
    Button option1, option2, option3, option4;
    boolean alreadyAnswered;
    EditText answerField;
    ImageButton answerSelect;
    boolean answerIsRight = false;
    AppDatabase db;

    public TaskFragment() {
        // Required empty public constructor
    }

    public static TaskFragment newInstance(int taskId) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putInt(TaskSearchActivity.TASK_ID, taskId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            taskId = getArguments().getInt(TaskSearchActivity.TASK_ID);
        }
        db = AppDatabase.getAppDatabase(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (taskId == null) {
            return inflater.inflate(R.layout.empty, container, false);
        } else {
            task = db.taskDao().getTask(taskId);

            if (task.isHasOptions()) {
                return inflater.inflate(R.layout.task_with_options, container, false);
            } else {
                return inflater.inflate(R.layout.task_without_options, container, false);
            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (taskId == null) {
            return;
        }
        WebView descriptionWebView = view.findViewById(R.id.task_description);
        descriptionWebView.getSettings().setSupportZoom(true);
        descriptionWebView.getSettings().setBuiltInZoomControls(true);
        descriptionWebView.getSettings().setDisplayZoomControls(false);
        // TODO move webview display code in helper function
        final String mimeType = "text/html";
        final String encoding = "UTF-8";
        String data = WebViewHelper.FIT_IMAGE +
                WebViewHelper.getCenteredText(task.getTitle(getContext())) +
                task.getDescription(getContext());
        descriptionWebView.loadDataWithBaseURL(WebViewHelper.ASSETS_FOLDER, data, mimeType,
                encoding, "");

        if (task.isHasOptions()) {
            option1 = view.findViewById(R.id.option1);
            option2 = view.findViewById(R.id.option2);
            option3 = view.findViewById(R.id.option3);
            option4 = view.findViewById(R.id.option4);
            option1.setText(task.getOption1(getContext()));
            option2.setText(task.getOption2(getContext()));
            option3.setText(task.getOption3(getContext()));
            option4.setText(task.getOption4(getContext()));
        } else {
            answerField = view.findViewById(R.id.answer_field);
            answerField.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    answerSelect.setEnabled(s.length() > 0);
                }
            });
            answerSelect = view.findViewById(R.id.answer_select);
            answerSelect.setEnabled(false);
        }
    }

    public void option1(View view) {
        checkOption(option1, 1);
    }

    public void option2(View view) {
        checkOption(option2, 2);
    }

    public void option3(View view) {
        checkOption(option3, 3);
    }

    public void option4(View view) {
        checkOption(option4, 4);
    }

    // TODO do we need to validate task data? maybe numericanswer > 4?
    private Button getOptionButton(int optionId) {
        if (optionId == 1) {
            return option1;
        } else if (optionId == 2) {
            return option2;
        } else if (optionId == 3) {
            return option3;
        } else {
            return option4;
        }
    }

    private void checkOption(Button option, int optionId) {
        if (option.getText().equals("")) {
            // empty Strings are never answers
            return;
        }
        if (alreadyAnswered) {
            return;
        }
        alreadyAnswered = true;
        int numericAnswer = task.getNumericAnswer();
        if (numericAnswer == optionId) {
            option.setBackgroundColor(Color.GREEN);
            answerIsRight = true;
            setSolved();
        } else {
            option.setBackgroundColor(Color.RED);
            getOptionButton(numericAnswer).setBackgroundColor(Color.GREEN);
            answerIsRight = false;
        }
    }

    private void removeKeyboardAndFocus() {
        InputMethodManager inputManager = (InputMethodManager)getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus()
                .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        answerField.clearFocus();
    }

    public void answerSelected(View view) {
        if (alreadyAnswered) {
            return;
        }
        alreadyAnswered = true;
        removeKeyboardAndFocus();
        String userAnswer = answerField.getText().toString();
        try {
            BigDecimal userAnswerValue = new Expression(userAnswer).eval();
            if (Math.abs(userAnswerValue.doubleValue() - task.getNumericAnswer()) < EPS) {
                answerField.setBackgroundColor(Color.GREEN);
                answerIsRight = true;
                setSolved();
            } else {
                answerField.setBackgroundColor(Color.RED);
                answerIsRight = false;
            }
        } catch (Expression.ExpressionException e) {
            answerField.setBackgroundColor(Color.RED);
            answerIsRight = false;
        }
    }

    private int getLevel() {
        TaskDao taskDao = AppDatabase.getAppDatabase(getContext()).taskDao();
        int solvedTasks = taskDao.getSolved().size();
        return ProgressActivity.getLevel(solvedTasks);
    }

    private void setSolved() {
        int levelBefore = getLevel();
        db.taskDao().updateSolved(taskId, true);
        int levelAfter = getLevel();
        if (levelAfter > levelBefore ) {
            showLevelUpdate(levelAfter);
        }
    }

    private void showLevelUpdate(int newLevel) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.textview_dialog, null);
        dialogBuilder.setView(dialogView);

        TextView textView = dialogView.findViewById(R.id.textview_dialog);
        textView.setText(getString(R.string.reached_level, newLevel));
        AlertDialog dialog = dialogBuilder.create();
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.close), (dialog1, which) -> dialog1.dismiss());
        dialog.show();
    }

    public void showHint() {
        showWebviewDialog(task.getHint(getContext()));
    }

    public void showAnswer() {
        showWebviewDialog(task.getAnswer(getContext()));
    }

    private void showWebviewDialog(String data) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.webview_dialog, null);
        dialogBuilder.setView(dialogView);

        WebView webView = dialogView.findViewById(R.id.webview_dialog);

        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);

        final String mimeType = "text/html";
        final String encoding = "UTF-8";
        webView.loadDataWithBaseURL(WebViewHelper.ASSETS_FOLDER,
                WebViewHelper.FIT_IMAGE + data, mimeType, encoding, "");

        AlertDialog dialog = dialogBuilder.create();
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.close), (dialog1, which) -> dialog1.dismiss());
        dialog.show();
    }

    public boolean alreadyAnswered() {
        return alreadyAnswered;
    }
    
    public boolean answerIsRight() {
        return answerIsRight;
    }
}
