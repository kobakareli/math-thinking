package tmand13.math_thinking;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by tmand on 6/23/2018.
 */

public class BaseActivity extends AppCompatActivity {
    BackgroundMusic backgroundMusic;

    Foreground.Listener listener = new Foreground.Listener(){
        public void onBecameForeground(){
            backgroundMusic.musicOn();
        }
        public void onBecameBackground(){
            backgroundMusic.musicOff();
        }
    };

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        backgroundMusic = BackgroundMusic.get(getApplicationContext());
        Foreground.get(this).addListener(listener);
        listener.onBecameForeground();
    }

    protected void onDestroy() {
        super.onDestroy();
        Foreground.get(this).removeListener(listener);
    }
}
