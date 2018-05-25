package tmand13.math_thinking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import tmand13.math_thinking.db.AppDatabase;
import tmand13.math_thinking.db.Article;
import tmand13.math_thinking.db.Task;
import tmand13.math_thinking.db.Test;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //insertTasks();
        //insertTests();
        //insertTaskWithOptions();
        //insertTwoDummyTasks();
    }

    private void insertTwoDummyTasks() {
        AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        for (int i = 104; i <= 105; i++) {
            String iStr = String.valueOf(i);
            db.taskDao().insert(new Task(i, "magari amocana"+String.valueOf(i), "gela",
                    "es aris descriptioni",
                    "d", "g", "g", "pasuxii",
                    "pasxii", 1, 2, 2, i%2==0,
                    "option pirveli", "d", "meore", "D",
                    "mesame", "D", "meotxe", "D"));
        }
    }

    private void insertTasks() {
        AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        for (int i = 3; i <= 100; i++) {
            db.taskDao().insert(new Task(i, "gela"+String.valueOf(i), "gela",
                    "d", "d", "g", "g", "g",
                    "g", 1, 2, 2, false,
                    "d", "d", "D", "D", "D",
                    "D", "D", "D"));
        }
    }

    private void insertTaskWithOptions() {
        AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        for (int i = 101; i <= 101; i++) {
            db.taskDao().insert(new Task(i, "gela"+String.valueOf(i), "gela",
                    "d", "d", "g", "g", "g",
                    "g", 1, 2, 2, true,
                    "d", "d", "D", "D", "D",
                    "D", "D", "D"));
        }
    }

    private void insertTests() {
        AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        for (int i = 3; i <= 100; i++) {
            db.testDao().insert(new Test(i, "test" + String.valueOf(i),
                    "ტესტი" + String.valueOf(i), ""));
        }
    }

    public void openCategories(View view) {
        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivity(intent);
    }

    public void openTasks(View view) {
        Intent intent = new Intent(this, TaskSearchActivity.class);
        startActivity(intent);
    }

    public void openTests(View view) {
        Intent intent = new Intent(this, TestSearchActivity.class);
        startActivity(intent);
    }
}
