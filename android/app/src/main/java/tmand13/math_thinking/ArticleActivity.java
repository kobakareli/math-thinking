package tmand13.math_thinking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;

import tmand13.math_thinking.db.AppDatabase;
import tmand13.math_thinking.db.Article;

public class ArticleActivity extends BaseActivity {

    Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        int articleId = intent.getIntExtra(CategoriesActivity.ARTICLE_ID, -1);

        System.out.println();

        final AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        article = db.articleDao().getArticle(articleId);
        String articleTitle = article.getTitle(getBaseContext());
        setTitle(articleTitle);

        // Capture the layout's TextView and set the string as its text
        WebView webView = findViewById(R.id.article_web_view);

        final String mimeType = "text/html";
        final String encoding = "UTF-8";
        webView.loadDataWithBaseURL("file:///android_asset/", articleTitle, mimeType, encoding,
                "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_article, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_article_test) {
            openTest();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openTest() {
        final AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        int testId = db.testCategoryDao().getTestCategory(article.getCategoryId()).getTestId();
        Intent intent = new Intent(this, TestActivity.class);
        intent.putExtra(TestActivity.TEST_ID, testId);
        startActivity(intent);
    }
}
