package tmand13.math_thinking;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by tmand on 7/5/2018.
 */

public class FirstTimeCalledWrapper {
    private static final String FIRST_TIME_CALLED = "first_time_called";

    private SharedPreferences prefs;
    public FirstTimeCalledWrapper(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean firstTimeCalled() {
        return prefs.getBoolean(FIRST_TIME_CALLED, true);
    }

    public void setFirstTimeCalled(boolean firstTimeCalled) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(FIRST_TIME_CALLED, firstTimeCalled);
        editor.apply();
    }
}
