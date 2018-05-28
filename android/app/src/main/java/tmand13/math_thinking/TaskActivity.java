package tmand13.math_thinking;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import tmand13.math_thinking.db.AppDatabase;
import tmand13.math_thinking.db.Task;

public class TaskActivity extends AppCompatActivity {
    //TODO control edittext based on answer type
    Task task;
    Button option1, option2, option3, option4;
    FloatingActionButton taskEndButton;
    boolean alreadyAnswered;
    EditText answerField;
    ImageButton answerSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        int taskId = intent.getIntExtra(TaskSearchActivity.TASK_ID, -1);

        AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        task = db.taskDao().getTask(taskId);

        if (task.isHasOptions()) {
            setContentView(R.layout.task_with_options);
        } else {
            setContentView(R.layout.task_without_options);
        }

        WebView titleWebView = findViewById(R.id.task_title);
        WebView descriptionWebView = findViewById(R.id.task_description);
        final String mimeType = "text/html";
        final String encoding = "UTF-8";
        titleWebView.loadDataWithBaseURL("file:///android_asset/",
                task.getTitleEn(), mimeType, encoding, "");
        descriptionWebView.loadDataWithBaseURL("file:///android_asset/",
                task.getDescriptionEn(), mimeType, encoding, "");

        if (task.isHasOptions()) {
            option1 = findViewById(R.id.option1);
            option2 = findViewById(R.id.option2);
            option3 = findViewById(R.id.option3);
            option4 = findViewById(R.id.option4);
            option1.setText(task.getOption1En());
            option2.setText(task.getOption2En());
            option3.setText(task.getOption3En());
            option4.setText(task.getOption4En());
        } else {
            answerField = findViewById(R.id.answer_field);
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
            answerSelect = findViewById(R.id.answer_select);
            answerSelect.setEnabled(false);
        }
        taskEndButton = findViewById(R.id.task_end_button);
        taskEndButton.setVisibility(View.INVISIBLE);
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
        showNextButton();
    }

    private void showNextButton() {
        taskEndButton.setVisibility(View.VISIBLE);
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

    public void answerSelected(View view) {
        String userAnswer = answerField.getText().toString();
        if (userAnswer.equals(task.getAnswerEn())) {
            answerField.setBackgroundColor(Color.GREEN);
        } else {
            answerField.setBackgroundColor(Color.RED);
        }
        showNextButton();
    }

    public void taskEnd(View view) {
        finish();
    }
}
