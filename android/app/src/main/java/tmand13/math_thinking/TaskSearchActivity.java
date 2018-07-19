package tmand13.math_thinking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.Spinner;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import tmand13.math_thinking.db.AppDatabase;
import tmand13.math_thinking.db.Task;

/// TODO support list pagination
public class TaskSearchActivity extends BaseActivity {
    public static final String TASK_ID = "task_id";

    CursorAdapter adapter;
    TaskFilterSortParametersWrapper wrapper;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_search);
        setTitle(R.string.tasks);

        ListView listView = findViewById(R.id.list_view_tasks);
        final AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        adapter = new CursorAdapter(getApplicationContext(), db.taskDao().getCursorAll()) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.task_search_item, parent, false);
            }

            @Override
            public void bindView(final View view, Context context, Cursor cursor) {
                Button button = view.findViewById(R.id.task_search_item);
                String title = cursor.getString(cursor.getColumnIndexOrThrow(Task.getTitleColumnName(getBaseContext())));
                final int taskId = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                button.setText(title);
                int solved = cursor.getInt(cursor.getColumnIndexOrThrow("solved"));
                if (solved > 0) {
                    button.setText(getString(R.string.solved_task_test, title));
                }
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openTask(taskId);
                    }
                });
            }
        };

        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                wrapper = new TaskFilterSortParametersWrapper(getApplicationContext());
                boolean hasOptions1, hasOptions2, solved1, solved2;
                int sortBy = wrapper.getSortBy();
                int answerSwitch = wrapper.getAnswerSwitch();
                int solvedSwitch = wrapper.getSolveSwitch();

                switch (answerSwitch) {
                    case 0:
                        hasOptions1 = hasOptions2 = true;
                        break;
                    case 1:
                        hasOptions1 = true;
                        hasOptions2 = false;
                        break;
                    default:
                        hasOptions1 = hasOptions2 = false;
                        break;
                }

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

                String titleConstraint = "%" + constraint.toString() + "%";

                if (LocaleHelper.getLanguage(getBaseContext()).equals(Locale.ENGLISH.getLanguage())) {
                    switch (sortBy) {
                        case 0:
                            return db.taskDao().getCursorOrderByCreationTimeEn(titleConstraint,
                                    hasOptions1, hasOptions2, solved1, solved2);
                        case 1:
                            return db.taskDao().getCursorOrderByUpdateTimeEn(titleConstraint,
                                    hasOptions1, hasOptions2, solved1, solved2);
                        default:
                            return db.taskDao().getCursorOrderByTitleEn(titleConstraint,
                                    hasOptions1, hasOptions2, solved1, solved2);
                    }
                } else {
                    switch (sortBy) {
                        case 0:
                            return db.taskDao().getCursorOrderByCreationTimeGe(titleConstraint,
                                    hasOptions1, hasOptions2, solved1, solved2);
                        case 1:
                            return db.taskDao().getCursorOrderByUpdateTimeGe(titleConstraint,
                                    hasOptions1, hasOptions2, solved1, solved2);
                        default:
                            return db.taskDao().getCursorOrderByTitleGe(titleConstraint,
                                    hasOptions1, hasOptions2, solved1, solved2);
                    }
                }
            }
        });

        listView.setAdapter(adapter);
        filterTasks("");
    }

    public void openTask(int taskId) {
        Intent intent = new Intent(this, TaskActivity.class);
        intent.putExtra(TASK_ID, taskId);
        startActivityForResult(intent, 2);
    }

    // TODO might use LoaderManager & CursorLoader to move away loading from UI thread
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Search task");
        // Before this searchview did not span the whole width
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterTasks(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void filterTasks(String titleConstraint) {
        adapter.changeCursor(adapter.getFilterQueryProvider().runQuery(titleConstraint));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_filter) {
            openTaskFilterSort();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (wrapper.isChanged()) {
                filterTasks(searchView.getQuery().toString());
                wrapper.refresh();
            }
        }
        if (requestCode == 2) {
            boolean answerIsRight = data.getBooleanExtra(TaskActivity.ANSWER_IS_RIGHT,
                    false);
            if (answerIsRight) {
                filterTasks(searchView.getQuery().toString());
            }
        }
    }

    private void openTaskFilterSort() {
        Intent intent = new Intent(this, TaskFilterSortActivity.class);
        wrapper = new TaskFilterSortParametersWrapper(getApplicationContext());
        startActivityForResult(intent, 1);
    }
}
