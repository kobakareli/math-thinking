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

    @Query("SELECT * FROM super_category_category where super_category_id = (:superCategoryId)")
    List<SuperCategoryCategory> getBySuperCategoryId(int superCategoryId);

    @Query("SELECT * FROM super_category_category where category_id = (:categoryId)")
    List<SuperCategoryCategory> getByCategoryId(int categoryId);

    @Insert
    void insert(SuperCategoryCategory superCategoryCategory);

    @Delete
    void delete(SuperCategoryCategory superCategoryCategory);
}
