package tmand13.math_thinking;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class TaskActivity extends AppCompatActivity {
    public static final String TASK_ID = "task_id";

    TaskFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Intent intent = getIntent();
        int taskId = intent.getIntExtra(TASK_ID, -1);

        fragment = TaskFragment.newInstance(taskId);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.task_fragment, fragment);
        transaction.commit();
    }

    public void option1(View view) {
        fragment.option1(view);
    }

    public void option2(View view) {
        fragment.option2(view);
    }

    public void option3(View view) {
        fragment.option3(view);
    }

    public void option4(View view) {
        fragment.option4(view);
    }

    public void answerSelected(View view) {
        fragment.answerSelected(view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_hint, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_hint) {
            fragment.showHint();
        }
        return super.onOptionsItemSelected(item);
    }
}
