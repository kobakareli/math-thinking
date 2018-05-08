package tmand13.math_thinking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import tmand13.math_thinking.db.AppDatabase;
import tmand13.math_thinking.db.Article;
import tmand13.math_thinking.db.Task;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //createDb();
    }

    private void createDb() {
        AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        for (int i = 3; i <= 100; i++) {
            db.taskDao().insert(new Task(i, "gela"+String.valueOf(i), "gela",
                    "d", "d", "g", "g", "g",
                    "g", 1, 2, 2, false,
                    "d", "d", "D", "D", "D",
                    "D", "D", "D"));
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
}
