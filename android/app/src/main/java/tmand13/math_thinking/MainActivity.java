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
import tmand13.math_thinking.db.Category;
import tmand13.math_thinking.db.SuperCategory;
import tmand13.math_thinking.db.Task;
import tmand13.math_thinking.db.Test;
import tmand13.math_thinking.db.TestCategory;

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
        insertSuperCategories();
        insertCategories();
        insertArticles();
        insertTestCategories();
    }

    private void insertTestCategories() {
        AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        db.testCategoryDao().insert(new TestCategory(1, 1, 1));
        db.testCategoryDao().insert(new TestCategory(2, 2, 2));
        db.testCategoryDao().insert(new TestCategory(3, 3, 3));
        db.testCategoryDao().insert(new TestCategory(4, 4, 4));
        db.testCategoryDao().insert(new TestCategory(5, 5, 5));
        db.testCategoryDao().insert(new TestCategory(6, 6, 6));
        db.testCategoryDao().insert(new TestCategory(7, 7, 7));
        db.testCategoryDao().insert(new TestCategory(8, 8, 8));
        db.testCategoryDao().insert(new TestCategory(9, 9, 9));
        db.testCategoryDao().insert(new TestCategory(10, 10, 10));
        db.testCategoryDao().insert(new TestCategory(11, 11, 11));
    }

    private void insertSuperCategories() {
        AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        db.superCategoryDao().insert(new SuperCategory(1, "Logic",
                "ლოგიკა"));
        db.superCategoryDao().insert(new SuperCategory(4, "Procents",
                "პროცენტები"));
        db.superCategoryDao().insert(new SuperCategory(2, "graph theory",
                "გრაფთა თეორია"));
        db.superCategoryDao().insert(new SuperCategory(3, "Geometry",
                "გეომეტრია"));
    }
    //TODO maybe give numbers like 1. Logic 1.1 Logic puzzles so on programmatically
    private void insertCategories() {
        AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        db.categoryDao().insert(new Category(1, "Puzzles",
                "პაზლები"));
        db.categoryDao().insert(new Category(2, "Crosswords1",
                "კროსვორდები1"));
        db.categoryDao().insert(new Category(3, "Crosswords2",
                "კროსვორდები2"));
        db.categoryDao().insert(new Category(4, "Crosswords3",
                "კროსვორდები3"));
        db.categoryDao().insert(new Category(5, "Crosswords4",
                "კროსვორდები4"));
        db.categoryDao().insert(new Category(6, "Crosswords5",
                "კროსვორდები5"));
        db.categoryDao().insert(new Category(7, "Crosswords6",
                "კროსვორდები6"));
        db.categoryDao().insert(new Category(8, "Crosswords7",
                "კროსვორდები7"));
        db.categoryDao().insert(new Category(9, "trees",
                "ხეები"));
        db.categoryDao().insert(new Category(10, "dijsktra",
                "დეიქსტრა"));
        db.categoryDao().insert(new Category(11, "Triangle",
                "სამკუთხედი"));
    }

    private void insertArticles() {
        AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        db.articleDao().insert(new Article(1, 1, 1,
                "Puzzles","პაზლები", "puzzles", "პაზლები"));
        db.articleDao().insert(new Article(2, 2, 1,
                "Crosswords1","კროსვორდები1", "Crosswords1", "კროსვორდები1"));
        db.articleDao().insert(new Article(3, 3, 1,
                "Crosswords2","კროსვორდები2", "Crosswords2", "კროსვორდები2"));
        db.articleDao().insert(new Article(4, 4, 1,
                "Crosswords3","კროსვორდები3", "Crosswords3", "კროსვორდები3"));
        db.articleDao().insert(new Article(5, 5, 1,
                "Crosswords4","კროსვორდები4", "Crosswords4", "კროსვორდები4"));
        db.articleDao().insert(new Article(6, 6, 1,
                "Crosswords5","კროსვორდები5", "Crosswords5", "კროსვორდები5"));
        db.articleDao().insert(new Article(7, 7, 1,
                "Crosswords6","კროსვორდები6", "Crosswords6", "კროსვორდები6"));
        db.articleDao().insert(new Article(8, 8, 1,
                "Crosswords7","კროსვორდები7", "Crosswords7", "კროსვორდები7"));

        db.articleDao().insert(new Article(9, 9, 2,
                "trees","ხეები", "trees", "ხეები"));
        db.articleDao().insert(new Article(10, 10, 2,
                "dijsktra","დეიქსტრა", "dijsktra", "დეიქსტრა"));

        db.articleDao().insert(new Article(11, 11, 3,
                "Triangle","სამკუთხედი", "Triangle", "სამკუთხედი"));
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
