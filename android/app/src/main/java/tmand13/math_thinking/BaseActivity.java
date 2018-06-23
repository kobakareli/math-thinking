package tmand13.math_thinking;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by tmand on 6/23/2018.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
}
