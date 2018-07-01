package tmand13.math_thinking.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import java.util.List;

/**
 * Created by tmand on 4/19/2018.
 */

@Dao
public interface TaskDao {
    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Query("SELECT t.title_en as title_en,t.title_ge as title_ge,t.task_id as _id FROM task t")
    Cursor getCursorAll();

    @Query("SELECT * FROM task where task_id = (:taskId)")
    Task getTask(int taskId);

    @Insert
    void insert(Task task);

    @Insert
    void insertAll(Task... tasks);

    @Delete
    void delete(Task task);

    // TODO maybe change LIKE to MATCH and add indexes as described
    // here: https://developer.android.com/guide/topics/search/search-dialog
    @Query("SELECT task_id as _id, title_en FROM task where title_en LIKE (:titlePrefix)")
    Cursor getCursorEn(String titlePrefix);

    // TODO maybe change LIKE to MATCH and add indexes as described
    // here: https://developer.android.com/guide/topics/search/search-dialog
    @Query("SELECT task_id as _id, title_ge FROM task where title_ge LIKE (:titlePrefix)")
    Cursor getCursorGe(String titlePrefix);
}
