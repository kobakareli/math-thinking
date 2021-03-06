package tmand13.math_thinking.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by tmand on 6/27/2018.
 */

@Dao
public interface TaskTestDao {
    @Query("SELECT * FROM task_test")
    List<TaskTest> getAll();

    @Query("SELECT * FROM task_test where test_id = (:testId)")
    List<TaskTest> getByTestId(int testId);

    @Query("SELECT * FROM task_test where task_id = (:taskId)")
    TaskTest getByTaskId(int taskId);

    @Insert
    void insert(TaskTest taskTest);

    @Insert
    void insertAll(TaskTest... taskTests);

    @Delete
    void delete(TaskTest taskTest);
}
