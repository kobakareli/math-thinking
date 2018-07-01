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
public interface SuperCategoryCategoryDao {
    @Query("SELECT * FROM super_category_category")
    List<SuperCategoryCategory> getAll();

    @Insert
    void insert(SuperCategoryCategory superCategoryCategory);

    @Insert
    void insertAll(SuperCategoryCategory... superCategoryCategories);

    @Delete
    void delete(SuperCategoryCategory superCategoryCategory);
}
