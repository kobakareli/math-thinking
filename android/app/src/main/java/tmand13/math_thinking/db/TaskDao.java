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

    @Query("SELECT t.title_en as title_en,t.solved as solved, t.title_ge as title_ge,t.task_id as _id FROM task t")
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

    @Query("SELECT task_id as _id, solved, title_en FROM task where title_en LIKE (:titlePrefix) AND " +
            "(has_options = (:hasOptions1) OR has_options = (:hasOptions2)) AND " +
            "(solved = (:solved1) OR solved = (:solved2)) ORDER BY creation_time")
    Cursor getCursorOrderByCreationTimeEn(String titlePrefix, boolean hasOptions1, boolean hasOptions2,
                       boolean solved1, boolean solved2);

    @Query("SELECT task_id as _id, solved, title_en FROM task where title_en LIKE (:titlePrefix) AND " +
            "(has_options = (:hasOptions1) OR has_options = (:hasOptions2)) AND " +
            "(solved = (:solved1) OR solved = (:solved2)) ORDER BY update_time")
    Cursor getCursorOrderByUpdateTimeEn(String titlePrefix, boolean hasOptions1, boolean hasOptions2,
                                          boolean solved1, boolean solved2);

    @Query("SELECT task_id as _id, solved, title_en FROM task where title_en LIKE (:titlePrefix) AND " +
            "(has_options = (:hasOptions1) OR has_options = (:hasOptions2)) AND " +
            "(solved = (:solved1) OR solved = (:solved2)) ORDER BY title_en")
    Cursor getCursorOrderByTitleEn(String titlePrefix, boolean hasOptions1, boolean hasOptions2,
                                          boolean solved1, boolean solved2);

    @Query("SELECT task_id as _id, solved, title_ge FROM task where title_ge LIKE (:titlePrefix) AND " +
            "(has_options = (:hasOptions1) OR has_options = (:hasOptions2)) AND " +
            "(solved = (:solved1) OR solved = (:solved2)) ORDER BY creation_time")
    Cursor getCursorOrderByCreationTimeGe(String titlePrefix, boolean hasOptions1, boolean hasOptions2,
                                            boolean solved1, boolean solved2);

    @Query("SELECT task_id as _id, solved, title_ge FROM task where title_ge LIKE (:titlePrefix) AND " +
            "(has_options = (:hasOptions1) OR has_options = (:hasOptions2)) AND " +
            "(solved = (:solved1) OR solved = (:solved2)) ORDER BY update_time")
    Cursor getCursorOrderByUpdateTimeGe(String titlePrefix, boolean hasOptions1, boolean hasOptions2,
                                          boolean solved1, boolean solved2);

    @Query("SELECT task_id as _id, solved, title_ge FROM task where title_ge LIKE (:titlePrefix) AND " +
            "(has_options = (:hasOptions1) OR has_options = (:hasOptions2)) AND " +
            "(solved = (:solved1) OR solved = (:solved2)) ORDER BY title_ge")
    Cursor getCursorOrderByTitleGe(String titlePrefix, boolean hasOptions1, boolean hasOptions2,
                                     boolean solved1, boolean solved2);

    @Query("SELECT * FROM task where solved > 0")
    List<Task> getSolved();

    @Query("UPDATE task SET solved = :solved WHERE task_id = :taskId")
    void updateSolved(int taskId, boolean solved);

    @Query("SELECT COUNT(task_id) FROM task")
    int getNumberOfTasks();

    @Query("SELECT COUNT(task_id) FROM task WHERE task_id = (:taskId)")
    int contains(int taskId);
}
