package tmand13.math_thinking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String articleId = intent.getStringExtra(CategoriesActivity.ARTICLE_ID);

        // Capture the layout's TextView and set the string as its text
        WebView webView = findViewById(R.id.article_web_view);

        final String mimeType = "text/html";
        final String encoding = "UTF-8";
        webView.loadDataWithBaseURL("file:///android_asset/", articleId, mimeType, encoding,
                "");
    }
}
