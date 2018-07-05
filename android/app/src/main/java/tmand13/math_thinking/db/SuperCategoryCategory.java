package tmand13.math_thinking.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by tmand on 6/27/2018.
 */

@Entity(tableName = "super_category_category", foreignKeys = {
        @ForeignKey(
                entity = SuperCategory.class,
                parentColumns = "super_category_id",
                childColumns = "super_category_id"
        ),
        @ForeignKey(
                entity = Category.class,
                parentColumns = "category_id",
                childColumns = "category_id"
        )})
public class SuperCategoryCategory {
    @PrimaryKey
    @ColumnInfo(name = "super_category_category_id")
    private int superCategoryCategoryId;

    @ColumnInfo(name = "super_category_id")
    private int superCategoryId;

    @ColumnInfo(name = "category_id")
    private int categoryId;

    public SuperCategoryCategory(int superCategoryCategoryId, int superCategoryId, int categoryId) {
        this.superCategoryCategoryId = superCategoryCategoryId;
        this.superCategoryId = superCategoryId;
        this.categoryId = categoryId;
    }

    public int getSuperCategoryCategoryId() {
        return superCategoryCategoryId;
    }

    public void setSuperCategoryCategoryId(int superCategoryCategoryId) {
        this.superCategoryCategoryId = superCategoryCategoryId;
    }

    public int getSuperCategoryId() {
        return superCategoryId;
    }

    public void setSuperCategoryId(int superCategoryId) {
        this.superCategoryId = superCategoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
