package tmand13.math_thinking.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import java.util.List;

/**
 * Created by tmand on 4/20/2018.
 */

@Dao
public interface TestDao {
    @Query("SELECT * FROM test")
    List<Test> getAll();

    @Query("SELECT t.title_en as title_en,t.solved as solved,t.title_ge as title_ge,t.test_id as _id FROM test t")
    Cursor getCursorAll();

    @Query("SELECT * FROM test where test_id = (:testId)")
    Test getTest(int testId);

    @Insert
    void insert(Test test);

    @Insert
    void insertAll(Test... tests);

    @Delete
    void delete(Test test);

    // TODO maybe change LIKE to MATCH and add indexes as described
    // here: https://developer.android.com/guide/topics/search/search-dialog

    @Query("SELECT test_id as _id, solved, title_en FROM test where title_en LIKE (:titleConstraint) AND " +
            "(solved = (:solved1) OR solved = (:solved2)) ORDER BY creation_time")
    Cursor getCursorOrderByCreationTimeEn(String titleConstraint, boolean solved1, boolean solved2);

    @Query("SELECT test_id as _id, solved, title_en FROM test where title_en LIKE (:titleConstraint) AND " +
            "(solved = (:solved1) OR solved = (:solved2)) ORDER BY update_time")
    Cursor getCursorOrderByUpdateTimeEn(String titleConstraint, boolean solved1, boolean solved2);

    @Query("SELECT test_id as _id, solved, title_en FROM test where title_en LIKE (:titleConstraint) AND " +
            "(solved = (:solved1) OR solved = (:solved2)) ORDER BY title_en")
    Cursor getCursorOrderByTitleEn(String titleConstraint, boolean solved1, boolean solved2);

    @Query("SELECT test_id as _id, solved, title_ge FROM test where title_ge LIKE (:titleConstraint) AND " +
            "(solved = (:solved1) OR solved = (:solved2)) ORDER BY creation_time")
    Cursor getCursorOrderByCreationTimeGe(String titleConstraint, boolean solved1, boolean solved2);

    @Query("SELECT test_id as _id, solved, title_ge FROM test where title_ge LIKE (:titleConstraint) AND " +
            "(solved = (:solved1) OR solved = (:solved2)) ORDER BY update_time")
    Cursor getCursorOrderByUpdateTimeGe(String titleConstraint, boolean solved1, boolean solved2);

    @Query("SELECT test_id as _id, solved, title_ge FROM test where title_ge LIKE (:titleConstraint) AND " +
            "(solved = (:solved1) OR solved = (:solved2)) ORDER BY title_ge")
    Cursor getCursorOrderByTitleGe(String titleConstraint, boolean solved1, boolean solved2);

    @Query("SELECT * FROM test where solved > 0")
    List<Test> getSolved();

    @Query("UPDATE test SET solved = :solved WHERE test_id = :testId")
    void updateSolved(int testId, boolean solved);

    @Query("SELECT COUNT(test_id) FROM test")
    int getNumberOfTests();

    @Query("SELECT COUNT(test_id) FROM test WHERE test_id = (:testId)")
    int contains(int testId);
}
