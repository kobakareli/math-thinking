package tmand13.math_thinking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.llollox.androidtoggleswitch.widgets.ToggleSwitch;

import java.util.ArrayList;
import java.util.List;

import tmand13.math_thinking.db.AppDatabase;
import tmand13.math_thinking.db.Category;
import tmand13.math_thinking.db.SuperCategory;

import static tmand13.math_thinking.TaskFilterSortParametersWrapper.MATCHES_ALL;

public class TaskFilterSortActivity extends BaseActivity {
    TaskFilterSortParametersWrapper wrapper;
    Spinner sortSpinner, categoryFilterSpinner, superCategoryFilterSpinner;
    ToggleSwitch answerSwitch;
    ToggleSwitch solveSwitch;
    ArrayList<String> categoriesTitles, superCategoriesTitles;
    List<Category> categories;
    List<SuperCategory> superCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_filter_sort);
        setTitle(R.string.task_filter_sort);

        wrapper = new TaskFilterSortParametersWrapper(getApplicationContext());

        configureSuperCategoryFilterSpinner();
        configureCategoryFilterSpinner();
        configureSortSpinner();

        answerSwitch = findViewById(R.id.answer_switch);
        answerSwitch.setOnChangeListener(i -> wrapper.setAnswerSwitch(i));

        solveSwitch = findViewById(R.id.solve_switch);
        solveSwitch.setOnChangeListener(i -> wrapper.setSolveSwitch(i));

        Button resetButton = findViewById(R.id.reset);
        resetButton.setOnClickListener(v -> reset());

        Button applyButton = findViewById(R.id.apply);
        applyButton.setOnClickListener(v -> {
            wrapper.apply();
            onBackPressed();
        });

        reset();
    }

    private void configureSortSpinner() {
        sortSpinner = findViewById(R.id.sort_spinner);
        ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(this,
                R.array.sort_by_parameters, android.R.layout.simple_spinner_item);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(sortAdapter);
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
                wrapper.setSortBy((int) id);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private void configureCategoryFilterSpinner() {
        AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        categories = db.categoryDao().getAll();
        categoriesTitles = new ArrayList<>();
        String anyCategory = getString(R.string.any_category);
        categoriesTitles.add(anyCategory);
        for (Category category : categories) {
            categoriesTitles.add(category.getTitle(getApplicationContext()));
        }
        categoryFilterSpinner = findViewById(R.id.filter_category_spinner);
        ArrayAdapter<String> categoryFilterAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categoriesTitles);
        categoryFilterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryFilterSpinner.setAdapter(categoryFilterAdapter);
        categoryFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
                String title = categoriesTitles.get(position);
                if (title.equals(anyCategory)) {
                    wrapper.setCategorySpinner(MATCHES_ALL);
                } else {
                    wrapper.setCategorySpinner(categories.get(position - 1).getCategoryId());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private void configureSuperCategoryFilterSpinner() {
        AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        superCategories = db.superCategoryDao().getAll();
        superCategoriesTitles = new ArrayList<>();
        String anySuperCategory = getString(R.string.any_super_category);
        superCategoriesTitles.add(anySuperCategory);
        for (SuperCategory superCategory : superCategories) {
            superCategoriesTitles.add(superCategory.getTitle(getApplicationContext()));
        }
        superCategoryFilterSpinner = findViewById(R.id.filter_super_category_spinner);
        ArrayAdapter<String> superCategoryFilterAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, superCategoriesTitles);
        superCategoryFilterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        superCategoryFilterSpinner.setAdapter(superCategoryFilterAdapter);
        superCategoryFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
                String title = superCategoriesTitles.get(position);
                if (title.equals(anySuperCategory)) {
                    wrapper.setSuperCategorySpinner(MATCHES_ALL);
                } else {
                    wrapper.setSuperCategorySpinner(superCategories.get(position - 1).getSuperCategoryId());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private void reset() {
        wrapper.reset();
        solveSwitch.setCheckedPosition(wrapper.getSolveSwitch());
        answerSwitch.setCheckedPosition(wrapper.getAnswerSwitch());
        sortSpinner.setSelection(wrapper.getSortBy());
        categoryFilterSpinner.setSelection(getCategorySelection());
        superCategoryFilterSpinner.setSelection(getSuperCategorySelection());
    }

    private int getCategorySelection() {
        int categorySpinner = wrapper.getCategorySpinner();
        if (categorySpinner == MATCHES_ALL) {
            return 0;
        }
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getCategoryId() == categorySpinner) {
                return i + 1;
            }
        }
        wrapper.setCategorySpinner(MATCHES_ALL);
        wrapper.applyCategorySpinner();
        return 0;
    }

    private int getSuperCategorySelection() {
        int superCategorySpinner = wrapper.getSuperCategorySpinner();
        if (superCategorySpinner == MATCHES_ALL) {
            return 0;
        }
        for (int i = 0; i < superCategories.size(); i++) {
            if (superCategories.get(i).getSuperCategoryId() == superCategorySpinner) {
                return i + 1;
            }
        }
        wrapper.setSuperCategorySpinner(MATCHES_ALL);
        wrapper.applySuperCategorySpinner();
        return 0;
    }
}
