package tmand13.math_thinking;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle(R.string.settings);

        Button languageButton = findViewById(R.id.language_button);
        languageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] listItems = {
                        getResources().getString(R.string.english),
                        getResources().getString(R.string.georgian),
                };
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingsActivity.this);
                mBuilder.setTitle(R.string.choose_language);
                mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            LocaleHelper.setLocale(getBaseContext(), Locale.ENGLISH.getLanguage());
                            relaunch(SettingsActivity.this);
                        } else if (which == 1) {
                            // using "fr", because somehow it does not work for "ka".
                            // TODO might fix in future
                            LocaleHelper.setLocale(getBaseContext(), Locale.FRENCH.getLanguage());
                            relaunch(SettingsActivity.this);
                        }
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });

        Button rateButton = findViewById(R.id.rate_button);
        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateApp();
            }
        });
    }

    public void rateApp() {
        try {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        }
        catch (ActivityNotFoundException e) {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }
    }

    private Intent rateIntentForUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        }
        else {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
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
