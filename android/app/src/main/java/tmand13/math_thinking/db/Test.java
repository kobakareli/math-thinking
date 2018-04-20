package tmand13.math_thinking.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tmand on 4/19/2018.
 */

@Entity(tableName = "test")
public class Test {
    @PrimaryKey
    @ColumnInfo(name = "test_id")
    private int testId;

    @NonNull
    @ColumnInfo(name = "title_en")
    private String titleEn;

    @NonNull
    @ColumnInfo(name = "title_ge")
    private String titleGe;

    @NonNull
    @ColumnInfo(name = "tasks_ids")
    private String tasksIds;

    public Test(int testId, @NonNull String titleEn, @NonNull String titleGe, @NonNull String tasksIds) {
        this.testId = testId;
        this.titleEn = titleEn;
        this.titleGe = titleGe;
        this.tasksIds = tasksIds;
    }

    public static String listToString(List<Integer> taskIds) {
        StringBuilder sb = new StringBuilder();
        String delimiter = "";
        for(Integer taskId : taskIds){
            sb.append(delimiter).append(String.valueOf(taskId));
            delimiter = ",";
        }
        return sb.toString();
    }

    public static List<Integer> stringToList(String taskIds) {
        List<String> taskIdsListStr = Arrays.asList(taskIds.split(","));
        List<Integer> taskIdsListInt = new ArrayList<>(taskIdsListStr.size());
        for (String taskId : taskIdsListStr) {
            taskIdsListInt.add(Integer.valueOf(taskId));
        }
        return taskIdsListInt;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    @NonNull
    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(@NonNull String titleEn) {
        this.titleEn = titleEn;
    }

    @NonNull
    public String getTitleGe() {
        return titleGe;
    }

    public void setTitleGe(@NonNull String titleGe) {
        this.titleGe = titleGe;
    }

    @NonNull
    public String getTasksIds() {
        return tasksIds;
    }

    public void setTasksIds(@NonNull String tasksIds) {
        this.tasksIds = tasksIds;
    }

    public List<Integer> getTasksIdsList() {
        return stringToList(tasksIds);
    }
}
