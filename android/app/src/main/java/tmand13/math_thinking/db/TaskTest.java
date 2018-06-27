package tmand13.math_thinking.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by tmand on 6/27/2018.
 */

@Entity(tableName = "task_test", foreignKeys = {
        @ForeignKey(
                entity = Task.class,
                parentColumns = "task_id",
                childColumns = "task_id"
        ),
        @ForeignKey(
                entity = Test.class,
                parentColumns = "test_id",
                childColumns = "test_id"
        )})
public class TaskTest {
    @PrimaryKey
    @ColumnInfo(name = "task_test_id")
    private int taskTestId;

    @ColumnInfo(name = "task_id")
    private int taskId;

    @ColumnInfo(name = "test_id")
    private int testId;

    public TaskTest(int taskTestId, int taskId, int testId) {
        this.taskTestId = taskTestId;
        this.taskId = taskId;
        this.testId = testId;
    }

    public int getTaskTestId() {
        return taskTestId;
    }

    public void setTaskTestId(int taskTestId) {
        this.taskTestId = taskTestId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }
}
