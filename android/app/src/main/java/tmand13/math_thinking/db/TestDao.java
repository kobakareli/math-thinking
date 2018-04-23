package tmand13.math_thinking.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by tmand on 4/20/2018.
 */

@Dao
public interface TestDao {
    @Query("SELECT * FROM test")
    List<Test> getAll();

    @Insert
    void insert(Test test);

    @Delete
    void delete(Test test);
}
