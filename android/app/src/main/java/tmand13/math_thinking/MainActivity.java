package tmand13.math_thinking;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Locale;

import tmand13.math_thinking.db.AppDatabase;
import tmand13.math_thinking.db.Article;
import tmand13.math_thinking.db.ArticleCategory;
import tmand13.math_thinking.db.Category;
import tmand13.math_thinking.db.SuperCategory;
import tmand13.math_thinking.db.SuperCategoryCategory;
import tmand13.math_thinking.db.Task;
import tmand13.math_thinking.db.TaskTest;
import tmand13.math_thinking.db.Test;
import tmand13.math_thinking.db.TestCategory;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_name);
        Context context = getApplicationContext();
        AppVersionCodeWrapper versionCodeWrapper = new AppVersionCodeWrapper(context);
        FirstTimeCalledWrapper firstTimeCalledWrapper = new FirstTimeCalledWrapper(context);
        SolvedTaskTestWrapper solvedTaskTestWrapper = new SolvedTaskTestWrapper(context);
        if (versionCodeWrapper.appIsUpdated()) {
            firstTimeCalledWrapper.setFirstTimeCalled(true);
            solvedTaskTestWrapper.fetchFromDB(context);
            AppDatabase.copyDBFileIfFirstTimeCalled(context);
            AppDatabase.rebuildAppDatabase(context);
            solvedTaskTestWrapper.saveInDB(context);
        } else {
            AppDatabase.copyDBFileIfFirstTimeCalled(context);
        }
        versionCodeWrapper.updateVersionCode();
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

    public void openProgress(View view) {
        Intent intent = new Intent(this, ProgressActivity.class);
        startActivity(intent);
    }

    public void openSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    protected void onDestroy() {
        super.onDestroy();
        BackgroundMusic.get(getApplicationContext()).musicOff();
    }
}
