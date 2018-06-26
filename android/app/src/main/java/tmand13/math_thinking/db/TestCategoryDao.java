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
public interface TestCategoryDao {
    @Query("SELECT * FROM test_category")
    List<TestCategory> getAll();

    @Query("SELECT * FROM test_category where category_id = (:categoryId)")
    TestCategory getTestCategory(int categoryId);

    @Insert
    void insert(TestCategory testCategory);

    @Delete
    void delete(TestCategory testCategory);
}
