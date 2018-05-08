package tmand13.math_thinking;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import tmand13.math_thinking.db.AppDatabase;
/// TODO support list pagination
public class TaskSearchActivity extends AppCompatActivity {
    CursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_search);
        ListView listView = findViewById(R.id.list_view_tasks);
        final AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        adapter = new CursorAdapter(getApplicationContext(), db.taskDao().getCursorAll()) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.task_search_item, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                Button button = view.findViewById(R.id.task_search_item);
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title_en"));
                button.setText(title);
            }
        };

        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                return db.taskDao().getCursor(constraint + "%");
            }
        });

        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) item.getActionView();
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
