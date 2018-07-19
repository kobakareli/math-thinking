package tmand13.math_thinking;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by tmand on 7/19/2018.
 */

public class MusicOnOffWrapper {
    private static final String MUSIC_IS_ON = "music_is_on";

    private SharedPreferences prefs;
    public MusicOnOffWrapper(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean musicIsOn() {
        return prefs.getBoolean(MUSIC_IS_ON, true);
    }

    public void setMusicIsOn(boolean musicIsOn) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(MUSIC_IS_ON, musicIsOn);
        editor.apply();
    }
}
