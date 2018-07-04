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
import android.support.v7.widget.SearchView;

import java.util.Locale;

import tmand13.math_thinking.db.AppDatabase;
import tmand13.math_thinking.db.Test;

/// TODO support list pagination
public class TestSearchActivity extends BaseActivity {
    public static final String TEST_ID = "test_id";

    CursorAdapter adapter;
    TestFilterSortParametersWrapper wrapper;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_search);
        setTitle(R.string.tests);

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
                String title = cursor.getString(cursor.getColumnIndexOrThrow(Test.getTitleColumnName(getBaseContext())));
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
                wrapper = new TestFilterSortParametersWrapper(getApplicationContext());
                boolean solved1, solved2;
                int sortBy = wrapper.getSortBy();
                int solvedSwitch = wrapper.getSolveSwitch();

                switch (solvedSwitch) {
                    case 0:
                        solved1 = solved2 = false;
                        break;
                    case 1:
                        solved1 = true;
                        solved2 = false;
                        break;
                    default:
                        solved1 = solved2 = true;
                        break;
                }

                String titlePrefix = constraint.toString() + "%";

                if (LocaleHelper.getLanguage(getBaseContext()).equals(Locale.ENGLISH.getLanguage())) {
                    switch (sortBy) {
                        case 0:
                            return db.testDao().getCursorOrderByCreationTimeEn(titlePrefix,
                                    solved1, solved2);
                        case 1:
                            return db.testDao().getCursorOrderByUpdateTimeEn(titlePrefix,
                                    solved1, solved2);
                        default:
                            return db.testDao().getCursorOrderByTitleEn(titlePrefix,
                                    solved1, solved2);
                    }
                } else {
                    switch (sortBy) {
                        case 0:
                            return db.testDao().getCursorOrderByCreationTimeGe(titlePrefix,
                                    solved1, solved2);
                        case 1:
                            return db.testDao().getCursorOrderByUpdateTimeGe(titlePrefix,
                                    solved1, solved2);
                        default:
                            return db.testDao().getCursorOrderByTitleGe(titlePrefix,
                                    solved1, solved2);
                    }
                }
            }
        });

        listView.setAdapter(adapter);
        filterTests("");
    }

    public void openTest(int testId) {
        Intent intent = new Intent(this, TestActivity.class);
        intent.putExtra(TEST_ID, testId);
        startActivity(intent);
    }

    // TODO might use LoaderManager & CursorLoader to move away loading from UI thread
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        searchView = (SearchView) item.getActionView();
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
                filterTests(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void filterTests(String titlePrefix) {
        adapter.changeCursor(adapter.getFilterQueryProvider().runQuery(titlePrefix));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_filter) {
            openTestFilterSort();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (wrapper.isChanged()) {
                filterTests(searchView.getQuery().toString());
                wrapper.refresh();
            }
        }
    }

    private void openTestFilterSort() {
        Intent intent = new Intent(this, TestFilterSortActivity.class);
        wrapper = new TestFilterSortParametersWrapper(getApplicationContext());
        startActivityForResult(intent, 1);
    }
}
