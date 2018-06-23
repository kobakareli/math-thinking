package tmand13.math_thinking;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button button = findViewById(R.id.language_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] listItems = {"English", "ქართული"};
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingsActivity.this);
                mBuilder.setTitle("Choose language...");
                mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            LocaleHelper.setLocale(getBaseContext(), "en");
                            relaunch(SettingsActivity.this);
                        } else if (which == 1) {
                            // using "fr", because somehow it does not work for "ka".
                            // TODO might fix in future
                            LocaleHelper.setLocale(getBaseContext(), "fr");
                            relaunch(SettingsActivity.this);
                        }
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });
    }

    public void relaunch(Activity activity) {
        // TODO right now this restarts the application
        // maybe it is possible to update the language without showing main page and
        // return back to settings page. If you set SettingsActivity instead of MainActivity
        // back button shuts down the app instead of going to main page.
        Intent intent = new Intent(activity, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        Runtime.getRuntime().exit(0);
        activity.finish();
    }
}
