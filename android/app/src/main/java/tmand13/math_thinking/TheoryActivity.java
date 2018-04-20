package tmand13.math_thinking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

public class TheoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theory);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.THEORY);

        // Capture the layout's TextView and set the string as its text
        WebView webView = findViewById(R.id.simpleTextView);

        final String mimeType = "text/html";
        final String encoding = "UTF-8";
        String html = message;
        System.out.println(html);
        webView.loadDataWithBaseURL("file:///android_asset/", html, mimeType, encoding,
                "");
    }
}
