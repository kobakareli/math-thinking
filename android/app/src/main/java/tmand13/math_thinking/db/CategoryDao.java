package tmand13.math_thinking.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by tmand on 4/19/2018.
 */

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM category")
    List<Category> getAll();

    @Insert
    void insert(Category category);

    @Insert
    void insertAll(Category... categories);

    @Delete
    void delete(Category category);

    @Query("SELECT COUNT(category_id) FROM category WHERE category_id = (:categoryId)")
    int contains(int categoryId);
}
