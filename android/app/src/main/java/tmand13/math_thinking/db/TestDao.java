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

    @Query("SELECT t.title_en as title_en,t.test_id as _id FROM test t")
    Cursor getCursorAll();

    @Query("SELECT * FROM test where test_id = (:testId)")
    Test getTest(int testId);

    @Insert
    void insert(Test test);

    @Delete
    void delete(Test test);

    // TODO maybe change LIKE to MATCH and add indexes as described
    // here: https://developer.android.com/guide/topics/search/search-dialog
    @Query("SELECT test_id as _id, title_en FROM test where title_en LIKE (:titlePrefix)")
    Cursor getCursor(String titlePrefix);
}
