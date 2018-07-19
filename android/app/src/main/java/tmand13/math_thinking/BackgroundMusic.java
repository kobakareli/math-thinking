package tmand13.math_thinking;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by tmand on 7/19/2018.
 */

public class BackgroundMusic {
    private static BackgroundMusic instance;

    public static BackgroundMusic get(Context context){
        if (instance == null) {
            instance = new BackgroundMusic(context);
        }
        return instance;
    }

    private Context context;

    private BackgroundMusic(Context context) {
        this.context = context;
    }

    public void musicOn() {
        Intent bms = new Intent(context, BackgroundMusicService.class);
        context.startService(bms);
    }

    public void musicOff() {
        Intent bms = new Intent(context, BackgroundMusicService.class);
        context.stopService(bms);
    }
}
