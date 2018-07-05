package tmand13.math_thinking;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

import tmand13.math_thinking.db.AppDatabase;
import tmand13.math_thinking.db.Task;
import tmand13.math_thinking.db.Test;

/**
 * Created by tmand on 7/5/2018.
 */

public class SolvedTaskTestWrapper {
    private static final String SOLVED_TASKS = "solved_tasks";
    private static final String SOLVED_TESTS = "solved_tests";

    private SharedPreferences prefs;
    public SolvedTaskTestWrapper(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    private Set<Integer> toIntegerSet(Set<String> s) {
        Set<Integer> ret = new HashSet<>();
        for (String id : s) {
            ret.add(Integer.valueOf(id));
        }
        return ret;
    }

    private Set<String> toStringSet(Set<Integer> s) {
        Set<String> ret = new HashSet<>();
        for (Integer id : s) {
            ret.add(String.valueOf(id));
        }
        return ret;
    }

    public Set<Integer> getSolvedTasks() {
        return toIntegerSet(prefs.getStringSet(SOLVED_TASKS, new HashSet<String>()));
    }

    public Set<Integer> getSolvedTests() {
        return toIntegerSet(prefs.getStringSet(SOLVED_TESTS, new HashSet<String>()));
    }

    private void setSolvedTasks(Set<Integer> s) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(SOLVED_TASKS, toStringSet(s));
        editor.apply();
    }

    private void setSolvedTests(Set<Integer> s) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(SOLVED_TESTS, toStringSet(s));
        editor.apply();
    }

    private void fetchTasksFromDB(Context context) {
        AppDatabase db = AppDatabase.getAppDatabase(context);
        Set<Integer> s = new HashSet<>();
        for (Task task : db.taskDao().getSolved()) {
            s.add(task.getTaskId());
        }
        setSolvedTasks(s);
    }

    private void fetchTestsFromDB(Context context) {
        AppDatabase db = AppDatabase.getAppDatabase(context);
        Set<Integer> s = new HashSet<>();
        for (Test test : db.testDao().getSolved()) {
            s.add(test.getTestId());
        }
        setSolvedTests(s);
    }

    public void fetchFromDB(Context context) {
        fetchTasksFromDB(context);
        fetchTestsFromDB(context);
    }

    private void saveTasksInDB(Context context) {
        AppDatabase db = AppDatabase.getAppDatabase(context);
        Set<Integer> s = getSolvedTasks();
        for (int taskId : s) {
            db.taskDao().updateSolved(taskId, true);
        }
    }

    private void saveTestsInDB(Context context) {
        AppDatabase db = AppDatabase.getAppDatabase(context);
        Set<Integer> s = getSolvedTests();
        for (int testId : s) {
            db.testDao().updateSolved(testId, true);
        }
    }

    public void saveInDB(Context context) {
        saveTasksInDB(context);
        saveTestsInDB(context);
    }
}
