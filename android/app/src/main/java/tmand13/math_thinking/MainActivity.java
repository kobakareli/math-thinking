package tmand13.math_thinking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import tmand13.math_thinking.db.AppDatabase;
import tmand13.math_thinking.db.Article;

public class MainActivity extends AppCompatActivity {
    public static final String THEORY = "theory";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        //System.out.println(db.theoryDao().getAll().size());
    }

    public void openTheory(View view) {
        /*Intent intent = new Intent(this, TheoryActivity.class);
        String content_html = AppDatabase.getAppDatabase(getApplicationContext())
                .theoryDao().getAll().get(0).getContentHtml();
        intent.putExtra(THEORY, content_html);
        startActivity(intent);*/
    }
}