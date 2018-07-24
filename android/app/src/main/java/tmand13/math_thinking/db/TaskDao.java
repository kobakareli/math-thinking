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

    @Query("WITH tasks as (WITH categories as (" +
            "SELECT category_id, super_category_id FROM super_category_category " +
            "WHERE (category_id = (:categoryId) or -1 = (:categoryId)) " +
            "AND (super_category_id = (:superCategoryId) or -1 = (:superCategoryId))) " +
            "SELECT * FROM task INNER JOIN " +
            "(SELECT task_id FROM task_test INNER JOIN " +
            "(SELECT test_id FROM test_category INNER JOIN categories on categories.category_id = test_category.category_id) AS tests " +
            "ON tests.test_id = task_test.test_id) as tasks ON tasks.task_id = task.task_id) " +

            "SELECT task_id as _id, solved, title_en FROM tasks where title_en LIKE (:titleConstraint) AND " +
            "(has_options = (:hasOptions1) OR has_options = (:hasOptions2)) AND " +
            "(solved = (:solved1) OR solved = (:solved2)) ORDER BY creation_time")
    Cursor getCursorOrderByCreationTimeEn(String titleConstraint, boolean hasOptions1, boolean hasOptions2,
                       boolean solved1, boolean solved2, int categoryId, int superCategoryId);

    @Query("WITH tasks as (WITH categories as (" +
            "SELECT category_id, super_category_id FROM super_category_category " +
            "WHERE (category_id = (:categoryId) or -1 = (:categoryId)) " +
            "AND (super_category_id = (:superCategoryId) or -1 = (:superCategoryId))) " +
            "SELECT * FROM task INNER JOIN " +
            "(SELECT task_id FROM task_test INNER JOIN " +
            "(SELECT test_id FROM test_category INNER JOIN categories on categories.category_id = test_category.category_id) AS tests " +
            "ON tests.test_id = task_test.test_id) as tasks ON tasks.task_id = task.task_id) " +

            "SELECT task_id as _id, solved, title_en FROM tasks where title_en LIKE (:titleConstraint) AND " +
            "(has_options = (:hasOptions1) OR has_options = (:hasOptions2)) AND " +
            "(solved = (:solved1) OR solved = (:solved2)) ORDER BY update_time")
    Cursor getCursorOrderByUpdateTimeEn(String titleConstraint, boolean hasOptions1, boolean hasOptions2,
                                          boolean solved1, boolean solved2, int categoryId, int superCategoryId);

    @Query("WITH tasks as (WITH categories as (" +
            "SELECT category_id, super_category_id FROM super_category_category " +
            "WHERE (category_id = (:categoryId) or -1 = (:categoryId)) " +
            "AND (super_category_id = (:superCategoryId) or -1 = (:superCategoryId))) " +
            "SELECT * FROM task INNER JOIN " +
            "(SELECT task_id FROM task_test INNER JOIN " +
            "(SELECT test_id FROM test_category INNER JOIN categories on categories.category_id = test_category.category_id) AS tests " +
            "ON tests.test_id = task_test.test_id) as tasks ON tasks.task_id = task.task_id) " +

            "SELECT task_id as _id, solved, title_en FROM tasks where title_en LIKE (:titleConstraint) AND " +
            "(has_options = (:hasOptions1) OR has_options = (:hasOptions2)) AND " +
            "(solved = (:solved1) OR solved = (:solved2)) ORDER BY title_en")
    Cursor getCursorOrderByTitleEn(String titleConstraint, boolean hasOptions1, boolean hasOptions2,
                                          boolean solved1, boolean solved2, int categoryId, int superCategoryId);

    @Query("WITH tasks as (WITH categories as (" +
            "SELECT category_id, super_category_id FROM super_category_category " +
            "WHERE (category_id = (:categoryId) or -1 = (:categoryId)) " +
            "AND (super_category_id = (:superCategoryId) or -1 = (:superCategoryId))) " +
            "SELECT * FROM task INNER JOIN " +
            "(SELECT task_id FROM task_test INNER JOIN " +
            "(SELECT test_id FROM test_category INNER JOIN categories on categories.category_id = test_category.category_id) AS tests " +
            "ON tests.test_id = task_test.test_id) as tasks ON tasks.task_id = task.task_id) " +

            "SELECT task_id as _id, solved, title_ge FROM tasks where title_ge LIKE (:titleConstraint) AND " +
            "(has_options = (:hasOptions1) OR has_options = (:hasOptions2)) AND " +
            "(solved = (:solved1) OR solved = (:solved2)) ORDER BY creation_time")
    Cursor getCursorOrderByCreationTimeGe(String titleConstraint, boolean hasOptions1, boolean hasOptions2,
                                            boolean solved1, boolean solved2, int categoryId, int superCategoryId);

    @Query("WITH tasks as (WITH categories as (" +
            "SELECT category_id, super_category_id FROM super_category_category " +
            "WHERE (category_id = (:categoryId) or -1 = (:categoryId)) " +
            "AND (super_category_id = (:superCategoryId) or -1 = (:superCategoryId))) " +
            "SELECT * FROM task INNER JOIN " +
            "(SELECT task_id FROM task_test INNER JOIN " +
            "(SELECT test_id FROM test_category INNER JOIN categories on categories.category_id = test_category.category_id) AS tests " +
            "ON tests.test_id = task_test.test_id) as tasks ON tasks.task_id = task.task_id) " +

            "SELECT task_id as _id, solved, title_ge FROM tasks where title_ge LIKE (:titleConstraint) AND " +
            "(has_options = (:hasOptions1) OR has_options = (:hasOptions2)) AND " +
            "(solved = (:solved1) OR solved = (:solved2)) ORDER BY update_time")
    Cursor getCursorOrderByUpdateTimeGe(String titleConstraint, boolean hasOptions1, boolean hasOptions2,
                                          boolean solved1, boolean solved2, int categoryId, int superCategoryId);

    @Query("WITH tasks as (WITH categories as (" +
            "SELECT category_id, super_category_id FROM super_category_category " +
            "WHERE (category_id = (:categoryId) or -1 = (:categoryId)) " +
            "AND (super_category_id = (:superCategoryId) or -1 = (:superCategoryId))) " +
            "SELECT * FROM task INNER JOIN " +
            "(SELECT task_id FROM task_test INNER JOIN " +
            "(SELECT test_id FROM test_category INNER JOIN categories on categories.category_id = test_category.category_id) AS tests " +
            "ON tests.test_id = task_test.test_id) as tasks ON tasks.task_id = task.task_id) " +

            "SELECT task_id as _id, solved, title_ge FROM tasks where title_ge LIKE (:titleConstraint) AND " +
            "(has_options = (:hasOptions1) OR has_options = (:hasOptions2)) AND " +
            "(solved = (:solved1) OR solved = (:solved2)) ORDER BY title_ge")
    Cursor getCursorOrderByTitleGe(String titleConstraint, boolean hasOptions1, boolean hasOptions2,
                                     boolean solved1, boolean solved2, int categoryId, int superCategoryId);

    @Query("SELECT * FROM task where solved > 0")
    List<Task> getSolved();

    @Query("UPDATE task SET solved = :solved WHERE task_id = :taskId")
    void updateSolved(int taskId, boolean solved);

    @Query("SELECT COUNT(task_id) FROM task")
    int getNumberOfTasks();

    @Query("SELECT COUNT(task_id) FROM task WHERE task_id = (:taskId)")
    int contains(int taskId);
}
