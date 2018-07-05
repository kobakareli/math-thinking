package tmand13.math_thinking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import tmand13.math_thinking.db.AppDatabase;

public class ProgressActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        TextView tasksSolved = findViewById(R.id.tasks_solved);
        TextView tests_solved = findViewById(R.id.tests_solved);
        TextView level = findViewById(R.id.level);

        AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        int numberOfTasks = db.taskDao().getNumberOfTasks();
        int numberOfTests = db.testDao().getNumberOfTests();

        SolvedTaskTestWrapper wrapper = new SolvedTaskTestWrapper(getApplicationContext());
        wrapper.fetchFromDB(getApplicationContext());
        int numberOfSolvedTasks = wrapper.getSolvedTasks().size();
        int numberOfSolvedTests = wrapper.getSolvedTests().size();

        tasksSolved.setText(getString(R.string.progress_tasks_solved, numberOfSolvedTasks,
                numberOfTasks));
        tests_solved.setText(getString(R.string.progress_tests_solved, numberOfSolvedTests,
                numberOfTests));

        int levelValue = 1;
        int curTasksNeeded = 1;
        while (numberOfSolvedTasks >= curTasksNeeded) {
            numberOfSolvedTasks -= curTasksNeeded;
            levelValue ++;
            curTasksNeeded ++;
        }
        level.setText(getString(R.string.progress_level, levelValue));
    }
}
