package tmand13.math_thinking;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tmand13.math_thinking.db.AppDatabase;
import tmand13.math_thinking.db.Test;

//TODO show answer button gvinda?

public class TestActivity extends AppCompatActivity {
    public static final String TEST_ID = "test_id";

    ArrayList<TaskFragment> fragments;
    ArrayList<TaskFragment> removedFragments;
    int curId;
    int numberOfTasks;
    int correct;
    int wrong;
    OnSwipeTouchListener onSwipeTouchListener;

    private void decreaseCurId() {
        if (fragments.size() > 0) {
            curId = (curId - 1 + fragments.size()) % fragments.size();
        }
    }

    private void increaseCurId() {
        if (fragments.size() > 0) {
            curId = (curId + 1) % fragments.size();
        }
    }

    private void showTaskFragment(int fragmentId) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.tesk_task_fragment, fragments.get(fragmentId));
        transaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Intent intent = getIntent();
        int testId = intent.getIntExtra(TEST_ID, -1);

        AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        Test test = db.testDao().getTest(testId);
        List<Integer> taskIdsList = test.getTasksIdsList();
        if (taskIdsList.isEmpty()) {
            // TODO any better dealing?
            testEnd();
        }

        numberOfTasks = taskIdsList.size();
        correct = 0;
        wrong = 0;
        curId = 0;

        fragments = new ArrayList<>();
        removedFragments = new ArrayList<>();
        for (int taskId : taskIdsList) {
            fragments.add(TaskFragment.newInstance(taskId));
        }
        showTaskFragment(curId);

        View testActivityView = findViewById(R.id.activity_test);
        onSwipeTouchListener = new OnSwipeTouchListener(TestActivity.this) {
            public void onSwipeRight() {
                if (fragments.get(curId).alreadyAnswered()) {
                    removedFragments.add(fragments.get(curId));
                    fragments.remove(curId);
                    decreaseCurId();
                    if (fragments.isEmpty()) {
                        testComplete();
                    }
                    increaseCurId();
                } else {
                    increaseCurId();
                }
                showTaskFragment(curId);
            }
            public void onSwipeLeft() {
                if (fragments.get(curId).alreadyAnswered()) {
                    removedFragments.add(fragments.get(curId));
                    fragments.remove(curId);
                    decreaseCurId();
                    if (fragments.isEmpty()) {
                        testComplete();
                    }
                    decreaseCurId();
                } else {
                    decreaseCurId();
                }
                showTaskFragment(curId);
            }
        };
        testActivityView.setOnTouchListener(onSwipeTouchListener);
    }

    public void option1(View view) {
        fragments.get(curId).option1(view);
    }

    public void option2(View view) {
        fragments.get(curId).option2(view);
    }

    public void option3(View view) {
        fragments.get(curId).option3(view);
    }

    public void option4(View view) {
        fragments.get(curId).option4(view);
    }

    public void answerSelected(View view) {
        fragments.get(curId).answerSelected(view);
    }

    public void testEnd() {
        super.onBackPressed();
    }

    public void testComplete() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.hint, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_hint) {
            fragments.get(curId).showHint();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e)
    {
        super.dispatchTouchEvent(e);
        return onSwipeTouchListener.getGestureDetector().onTouchEvent(e);
    }
}
