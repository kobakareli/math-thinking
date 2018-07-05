package tmand13.math_thinking;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by tmand on 7/5/2018.
 */

public class AppVersionCodeWrapper {
    private static final String APP_VERSION_CODE = "app_version_code";

    private SharedPreferences prefs;
    public AppVersionCodeWrapper(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean appIsUpdated() {
        if (!prefs.contains(APP_VERSION_CODE)) {
            updateVersionCode();
            return false;
        }
        return prefs.getInt(APP_VERSION_CODE, -1) != BuildConfig.VERSION_CODE;
    }

    public void updateVersionCode() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(APP_VERSION_CODE, BuildConfig.VERSION_CODE);
        editor.apply();
    }
}
