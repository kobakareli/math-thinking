package tmand13.math_thinking.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by tmand on 4/19/2018.
 */
@Entity(tableName = "super_category")
public class SuperCategory {
    @PrimaryKey
    @ColumnInfo(name = "super_category_id")
    private int superCategoryId;

    @NonNull
    @ColumnInfo(name = "title_en")
    private String titleEn;

    @NonNull
    @ColumnInfo(name = "title_ge")
    private String titleGe;

    public SuperCategory(int superCategoryId, @NonNull String titleEn, @NonNull String titleGe) {
        this.superCategoryId = superCategoryId;
        this.titleEn = titleEn;
        this.titleGe = titleGe;
    }

    public int getSuperCategoryId() {
        return superCategoryId;
    }

    public void setSuperCategoryId(int superCategoryId) {
        this.superCategoryId = superCategoryId;
    }

    @NonNull
    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(@NonNull String titleEn) {
        this.titleEn = titleEn;
    }

    @NonNull
    public String getTitleGe() {
        return titleGe;
    }

    public void setTitleGe(@NonNull String titleGe) {
        this.titleGe = titleGe;
    }
}
