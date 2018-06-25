package tmand13.math_thinking;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Locale;

import tmand13.math_thinking.db.AppDatabase;
import tmand13.math_thinking.db.Article;
import tmand13.math_thinking.db.Task;
import tmand13.math_thinking.db.Test;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_name);
        //insertData();
    }

    private void insertData() {
        insertTasks();
        insertTaskWithOptions();
        insertTwoDummyTasks();
        insertTests();
    }

    private void insertTwoDummyTasks() {
        AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        for (int i = 102; i <= 105; i++) {
            String iStr = String.valueOf(i);
            db.taskDao().insert(new Task(i, "magari amocana"+String.valueOf(i), "მაგარი ამოცანა",
                    "es aris descriptioni",
                    "ეს არის დესკრიპშენი", "g", "ჰინტი", "pasuxii",
                    "პასუხი", 1, 2, 2, i%2==0,
                    "option pirveli", "პირველი", "meore", "მეორე",
                    "mesame", "მესამე", "meotxe", "მეოთხე"));
        }
    }

    private void insertTasks() {
        AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        for (int i = 1; i <= 100; i++) {
            db.taskDao().insert(new Task(i, "gela"+String.valueOf(i), "გელა",
                    "d", "დ", "g", "ჰინტი", "g",
                    "პასუხი", 1, 2, 2, false,
                    "d", "დ", "D", "დდ", "d",
                    "დდდ", "D", "დდდდ"));
        }
    }

    private void insertTaskWithOptions() {
        AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        for (int i = 101; i <= 101; i++) {
            db.taskDao().insert(new Task(i, "gela"+String.valueOf(i), "გელსონა",
                    "d", "აეეეე", "g", "გგ", "g",
                    "პასუხი", 1, 2, 2, true,
                    "d", "დ", "D", "დდ", "D",
                    "დდდ", "D", "დდდდ"));
        }
    }

    private void insertTests() {
        AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        for (int i = 1; i <= 100; i++) {
            db.testDao().insert(new Test(i, "test" + String.valueOf(i),
                    "ტესტი" + String.valueOf(i), "1,2,104"));
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

    public void openSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
