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
    TestCategory getByCategoryId(int categoryId);

    @Query("SELECT * FROM test_category where test_id = (:testId)")
    TestCategory getByTestId(int testId);

    @Insert
    void insert(TestCategory testCategory);

    @Insert
    void insertAll(TestCategory... testCategories);

    @Delete
    void delete(TestCategory testCategory);
}
