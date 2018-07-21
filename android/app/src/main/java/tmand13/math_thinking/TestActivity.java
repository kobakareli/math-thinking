package tmand13.math_thinking;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tmand13.math_thinking.db.AppDatabase;
import tmand13.math_thinking.db.TaskTest;
import tmand13.math_thinking.db.Test;

import static tmand13.math_thinking.CategoriesActivity.ARTICLE_ID;

public class TestActivity extends BaseActivity {
    public static final String ALL_IS_RIGHT = "all_is_right";

    ArrayList<TaskFragment> fragments;
    ArrayList<TaskFragment> removedFragments;
    int curId;
    int numberOfTasks;
    int right;
    int wrong;
    OnSwipeTouchListener onSwipeTouchListener;
    int testId;
    AppDatabase db;

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
        // TODO use some kind of animation
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
        testId = intent.getIntExtra(TestSearchActivity.TEST_ID, -1);

        db = AppDatabase.getAppDatabase(getApplicationContext());
        Test test = db.testDao().getTest(testId);
        List<TaskTest> taskTests = db.taskTestDao().getByTestId(test.getTestId());
        List<Integer> taskIdsList = new ArrayList<>();
        for (TaskTest taskTest : taskTests) {
            taskIdsList.add(taskTest.getTaskId());
        }
        if (taskIdsList.isEmpty()) {
            // TODO any better dealing?
            testEnd();
        }

        numberOfTasks = taskIdsList.size();
        right = 0;
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

        getSupportActionBar().setCustomView(R.layout.test_action_bar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        ImageView showArticleView = findViewById(R.id.article_show);
        showArticleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
                int categoryId = db.testCategoryDao().getByTestId(testId).getCategoryId();
                int articleId = db.articleCategoryDao().getByCategoryId(categoryId).getArticleId();
                openArticle(articleId);
            }
        });

        updateNumberOfTasksOnView();
    }

    private void openArticle(int articleId) {
        Intent intent = new Intent(this, ArticleActivity.class);
        intent.putExtra(ARTICLE_ID, articleId);
        startActivity(intent);
    }

    private void updateScore(boolean alreadyAnsweredBefore) {
        boolean alreadyAnsweredAfter = fragments.get(curId).alreadyAnswered();
        boolean answerIsRight = fragments.get(curId).answerIsRight();
        if (!alreadyAnsweredBefore && alreadyAnsweredAfter) {
            if (answerIsRight) {
                right++;
                updateRightOnView();
                if (allIsRight()){
                    setSolved();
                }
            } else {
                wrong++;
                updateWrongOnView();
            }
        }
    }

    private void setSolved() {
        db.testDao().updateSolved(testId, true);
    }

    private boolean allIsRight() {
        return right == numberOfTasks;
    }

    public void option1(View view) {
        boolean alreadyAnsweredBefore = fragments.get(curId).alreadyAnswered();
        fragments.get(curId).option1(view);
        updateScore(alreadyAnsweredBefore);
    }

    private void updateRightOnView() {
        TextView textView = findViewById(R.id.test_score_right_number);
        textView.setText(String.valueOf(right));
    }

    private void updateWrongOnView() {
        TextView textView = findViewById(R.id.test_score_wrong_number);
        textView.setText(String.valueOf(wrong));
    }

    private void updateNumberOfTasksOnView() {
        TextView textView = findViewById(R.id.test_score_tasks_number);
        textView.setText(String.valueOf(numberOfTasks));
    }

    public void option2(View view) {
        boolean alreadyAnsweredBefore = fragments.get(curId).alreadyAnswered();
        fragments.get(curId).option2(view);
        updateScore(alreadyAnsweredBefore);
    }

    public void option3(View view) {
        boolean alreadyAnsweredBefore = fragments.get(curId).alreadyAnswered();
        fragments.get(curId).option3(view);
        updateScore(alreadyAnsweredBefore);
    }

    public void option4(View view) {
        boolean alreadyAnsweredBefore = fragments.get(curId).alreadyAnswered();
        fragments.get(curId).option4(view);
        updateScore(alreadyAnsweredBefore);
    }

    public void answerSelected(View view) {
        boolean alreadyAnsweredBefore = fragments.get(curId).alreadyAnswered();
        fragments.get(curId).answerSelected(view);
        updateScore(alreadyAnsweredBefore);
    }

    public void testEnd() {
        onBackPressed();
    }

    public void testComplete() {
        onBackPressed();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        super.dispatchTouchEvent(e);
        return onSwipeTouchListener.getGestureDetector().onTouchEvent(e);
    }

    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        data.putExtra(ALL_IS_RIGHT, allIsRight());
        setResult(RESULT_OK, data);
        super.onBackPressed();
    }
}
