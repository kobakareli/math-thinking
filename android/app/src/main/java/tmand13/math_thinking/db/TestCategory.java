package tmand13.math_thinking.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by tmand on 6/27/2018.
 */

@Entity(tableName = "test_category", foreignKeys = {
        @ForeignKey(
                entity = Test.class,
                parentColumns = "test_id",
                childColumns = "test_id"
        ),
        @ForeignKey(
                entity = Category.class,
                parentColumns = "category_id",
                childColumns = "category_id"
        )})
public class TestCategory {
    @PrimaryKey
    @ColumnInfo(name = "test_category_id")
    private int testCategoryId;

    @ColumnInfo(name = "category_id")
    private int categoryId;

    @ColumnInfo(name = "test_id")
    private int testId;

    public TestCategory(int testCategoryId, int categoryId, int testId) {
        this.testCategoryId = testCategoryId;
        this.categoryId = categoryId;
        this.testId = testId;
    }

    public int getTestCategoryId() {
        return testCategoryId;
    }

    public void setTestCategoryId(int testCategoryId) {
        this.testCategoryId = testCategoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }
}
