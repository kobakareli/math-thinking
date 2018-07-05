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
public interface SuperCategoryDao {
    @Query("SELECT * FROM super_category")
    List<SuperCategory> getAll();

    @Insert
    void insert(SuperCategory superCategory);

    @Insert
    void insertAll(SuperCategory... superCategories);

    @Delete
    void delete(SuperCategory superCategory);
}
