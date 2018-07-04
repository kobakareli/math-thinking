package tmand13.math_thinking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.llollox.androidtoggleswitch.widgets.ToggleSwitch;

public class TaskFilterSortActivity extends AppCompatActivity {
    TaskFilterSortParametersWrapper wrapper;
    Spinner sortSpinner;
    ToggleSwitch answerSwitch;
    ToggleSwitch solveSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_filter_sort);
        setTitle(R.string.task_filter_sort);

        wrapper = new TaskFilterSortParametersWrapper(getApplicationContext());

        sortSpinner = (Spinner) findViewById(R.id.sort_spinner);
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


        answerSwitch = findViewById(R.id.answer_switch);
        answerSwitch.setOnChangeListener(new ToggleSwitch.OnChangeListener() {
            @Override
            public void onToggleSwitchChanged(int i) {
                wrapper.setAnswerSwitch(i);
            }
        });

        solveSwitch = findViewById(R.id.solve_switch);
        solveSwitch.setOnChangeListener(new ToggleSwitch.OnChangeListener() {
            @Override
            public void onToggleSwitchChanged(int i) {
                wrapper.setSolveSwitch(i);
            }
        });

        Button resetButton = findViewById(R.id.reset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        Button applyButton = findViewById(R.id.apply);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrapper.apply();
                onBackPressed();
            }
        });

        reset();
    }

    private void reset() {
        wrapper.reset();
        solveSwitch.setCheckedPosition(wrapper.getSolveSwitch());
        answerSwitch.setCheckedPosition(wrapper.getAnswerSwitch());
        sortSpinner.setSelection(wrapper.getSortBy());
    }
}
