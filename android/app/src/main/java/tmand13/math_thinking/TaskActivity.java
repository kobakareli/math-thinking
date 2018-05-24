package tmand13.math_thinking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import tmand13.math_thinking.db.AppDatabase;
import tmand13.math_thinking.db.Task;

public class TaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Intent intent = getIntent();
        int taskId = intent.getIntExtra(TaskSearchActivity.TASK_ID, -1);

        AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        Task task = db.taskDao().getTask(taskId);
        WebView titleWebView = findViewById(R.id.task_title);
        WebView descriptionWebView = findViewById(R.id.task_description);

        final String mimeType = "text/html";
        final String encoding = "UTF-8";
        titleWebView.loadDataWithBaseURL("file:///android_asset/",
                task.getTitleEn(), mimeType, encoding, "");
        descriptionWebView.loadDataWithBaseURL("file:///android_asset/",
                task.getDescriptionEn(), mimeType, encoding, "");
    }

    public void option1(View view) {

    }

    public void option2(View view) {

    }

    public void option3(View view) {

    }

    public void option4(View view) {

    }
}
