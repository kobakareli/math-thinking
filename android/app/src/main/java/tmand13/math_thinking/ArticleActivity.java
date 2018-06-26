package tmand13.math_thinking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import tmand13.math_thinking.db.AppDatabase;

public class ArticleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        int articleId = intent.getIntExtra(CategoriesActivity.ARTICLE_ID, -1);

        System.out.println();

        final AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        String article = db.articleDao().getArticle(articleId).getText(getBaseContext());

        setTitle(article);

        // Capture the layout's TextView and set the string as its text
        WebView webView = findViewById(R.id.article_web_view);

        final String mimeType = "text/html";
        final String encoding = "UTF-8";
        webView.loadDataWithBaseURL("file:///android_asset/", article, mimeType, encoding,
                "");
    }
}
