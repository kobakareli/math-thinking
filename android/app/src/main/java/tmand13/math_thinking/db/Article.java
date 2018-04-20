package tmand13.math_thinking.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by tmand on 4/17/2018.
 */

//TODO maybe add indexes to speed up
@Entity(tableName = "article", foreignKeys = {
        @ForeignKey(
                entity = Category.class,
                parentColumns = "category_id",
                childColumns = "category_id"
        ),
        @ForeignKey(
                entity = SuperCategory.class,
                parentColumns = "super_category_id",
                childColumns = "super_category_id"
        )})
public class Article {
    @PrimaryKey
    @ColumnInfo(name = "article_id")
    private int articleId;

    @ColumnInfo(name = "category_id")
    private int categoryId;

    @ColumnInfo(name = "super_category_id")
    private int superCategoryId;

    @NonNull
    @ColumnInfo(name = "title_en")
    private String titleEn;

    @NonNull
    @ColumnInfo(name = "title_ge")
    private String titleGE;

    @NonNull
    @ColumnInfo(name = "text_en")
    private String textEn;

    @NonNull
    @ColumnInfo(name = "text_ge")
    private String textGe;

    public Article(int articleId, int categoryId, int superCategoryId, @NonNull String titleEn,
                   @NonNull String titleGE, @NonNull String textEn, @NonNull String textGe) {
        this.articleId = articleId;
        this.categoryId = categoryId;
        this.superCategoryId = superCategoryId;
        this.titleEn = titleEn;
        this.titleGE = titleGE;
        this.textEn = textEn;
        this.textGe = textGe;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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
    public String getTitleGE() {
        return titleGE;
    }

    public void setTitleGE(@NonNull String titleGE) {
        this.titleGE = titleGE;
    }

    @NonNull
    public String getTextEn() {
        return textEn;
    }

    public void setTextEn(@NonNull String textEn) {
        this.textEn = textEn;
    }

    @NonNull
    public String getTextGe() {
        return textGe;
    }

    public void setTextGe(@NonNull String textGe) {
        this.textGe = textGe;
    }
}
