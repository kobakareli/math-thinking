package tmand13.math_thinking;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import tmand13.math_thinking.db.AppDatabase;
import tmand13.math_thinking.db.Task;
// TODO remove unused libraries and solve all intellij idea
// advice
public class TaskFragment extends Fragment {
    public static final String TASK_ID = "task_id";

    int taskId;
    //TODO control edittext based on answer type
    Task task;
    Button option1, option2, option3, option4;
    boolean alreadyAnswered;
    EditText answerField;
    ImageButton answerSelect;

    public TaskFragment() {
        // Required empty public constructor
    }

    public static TaskFragment newInstance(int taskId) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putInt(TASK_ID, taskId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            taskId = getArguments().getInt(TASK_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AppDatabase db = AppDatabase.getAppDatabase(getContext());
        task = db.taskDao().getTask(taskId);

        int layout;
        if (task == null) {
            layout = R.layout.empty;
        } else if (task.isHasOptions()) {
            layout = R.layout.task_with_options;
        } else {
            layout = R.layout.task_without_options;
        }
        return inflater.inflate(layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (task == null) {
            return;
        }
        TextView titleTextView = view.findViewById(R.id.task_title);
        WebView descriptionWebView = view.findViewById(R.id.task_description);
        // TODO move webview display code in helper function
        final String mimeType = "text/html";
        final String encoding = "UTF-8";
        titleTextView.setText(task.getTitleEn());
        descriptionWebView.loadDataWithBaseURL("file:///android_asset/",
                task.getDescriptionEn(), mimeType, encoding, "");

        if (task.isHasOptions()) {
            option1 = view.findViewById(R.id.option1);
            option2 = view.findViewById(R.id.option2);
            option3 = view.findViewById(R.id.option3);
            option4 = view.findViewById(R.id.option4);
            option1.setText(task.getOption1En());
            option2.setText(task.getOption2En());
            option3.setText(task.getOption3En());
            option4.setText(task.getOption4En());
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
        if (alreadyAnswered) {
            return;
        }
        alreadyAnswered = true;
        int numericAnswer = task.getNumericAnswer();
        if (numericAnswer == optionId) {
            option.setBackgroundColor(Color.GREEN);
        } else {
            option.setBackgroundColor(Color.RED);
            getOptionButton(numericAnswer).setBackgroundColor(Color.GREEN);
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
        removeKeyboardAndFocus();
        //todo close keyboard
        String userAnswer = answerField.getText().toString();
        if (userAnswer.equals(task.getAnswerEn())) {
            answerField.setBackgroundColor(Color.GREEN);
        } else {
            answerField.setBackgroundColor(Color.RED);
        }
    }

    public void showHint() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.hint_dialog);
        dialog.setTitle("Hint");

        WebView hintWebView = (WebView) dialog.findViewById(R.id.hint_webview);
        final String mimeType = "text/html";
        final String encoding = "UTF-8";
        hintWebView.loadDataWithBaseURL("file:///android_asset/",
                task.getHintEn(), mimeType, encoding, "");

        dialog.show();
    }
}