package tmand13.math_thinking;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SearchView;

import tmand13.math_thinking.db.AppDatabase;
/// TODO support list pagination
public class TestSearchActivity extends AppCompatActivity {
    public static final String TEST_ID = "test_id";

    CursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_search);

        ListView listView = findViewById(R.id.list_view_tests);
        final AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        adapter = new CursorAdapter(getApplicationContext(), db.testDao().getCursorAll()) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.test_search_item, parent, false);
            }

            @Override
            public void bindView(final View view, Context context, Cursor cursor) {
                Button button = view.findViewById(R.id.test_search_item);
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title_en"));
                final int testId = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                button.setText(title);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openTest(testId);
                    }
                });
            }
        };

        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                return db.testDao().getCursor(constraint + "%");
            }
        });

        listView.setAdapter(adapter);
    }

    public void openTest(int testId) {
        /*Intent intent = new Intent(this, TestActivi.class);
        intent.putExtra(TEST_ID, testId);
        startActivity(intent);
        */
    }

    // TODO might use LoaderManager & CursorLoader to move away loading from UI thread
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Search test");
        // Before this searchview did not span the whole width
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.changeCursor(adapter.getFilterQueryProvider().runQuery(newText));
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
