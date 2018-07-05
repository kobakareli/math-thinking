package tmand13.math_thinking;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by tmand on 7/4/2018.
 */

public class TaskFilterSortParametersWrapper {
    private static final String SORT_BY = "TaskFilterSortParametersWrapper.SORT_BY";
    private static final String ANSWER_SWITCH = "TaskFilterSortParametersWrapper.ANSWER_SWITCH";
    private static final String SOLVE_SWITCH = "TaskFilterSortParametersWrapper.SOLVE_SWITCH";

    private SharedPreferences prefs;
    private int sortBy;
    private int answerSwitch;
    private int solveSwitch;

    public TaskFilterSortParametersWrapper(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        reset();
    }

    public void reset() {
        sortBy = getSortBy();
        answerSwitch = getAnswerSwitch();
        solveSwitch = getSolveSwitch();
    }

    public boolean isChanged() {
        return sortBy != getSortBy() || answerSwitch != getAnswerSwitch() ||
                solveSwitch != getSolveSwitch();
    }

    public void refresh() {
        sortBy = getSortBy();
        answerSwitch = getAnswerSwitch();
        solveSwitch = getSolveSwitch();
    }

    public void apply() {
        applySortBy();
        applyAnswerSwitch();
        applySolveSwitch();
    }

    public int getSortBy() {
        return prefs.getInt(SORT_BY, 0);
    }

    public void applySortBy() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(SORT_BY, sortBy);
        editor.apply();
    }

    public int getAnswerSwitch() {
        return prefs.getInt(ANSWER_SWITCH, 1);
    }

    public void applyAnswerSwitch() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(ANSWER_SWITCH, answerSwitch);
        editor.apply();
    }

    public int getSolveSwitch() {
        return prefs.getInt(SOLVE_SWITCH, 1);
    }

    public void applySolveSwitch() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(SOLVE_SWITCH, solveSwitch);
        editor.apply();
    }

    public void setSolveSwitch(int solveSwitch) {
        this.solveSwitch = solveSwitch;
    }

    public void setSortBy(int sortBy) {
        this.sortBy = sortBy;
    }

    public void setAnswerSwitch(int answerSwitch) {
        this.answerSwitch = answerSwitch;
    }

}
