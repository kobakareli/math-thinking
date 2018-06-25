package tmand13.math_thinking.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Locale;

import tmand13.math_thinking.LocaleHelper;

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
    private String titleGe;

    @NonNull
    @ColumnInfo(name = "text_en")
    private String textEn;

    @NonNull
    @ColumnInfo(name = "text_ge")
    private String textGe;

    public Article(int articleId, int categoryId, int superCategoryId, @NonNull String titleEn,
                   @NonNull String titleGe, @NonNull String textEn, @NonNull String textGe) {
        this.articleId = articleId;
        this.categoryId = categoryId;
        this.superCategoryId = superCategoryId;
        this.titleEn = titleEn;
        this.titleGe = titleGe;
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
    public String getTitleGe() {
        return titleGe;
    }

    public void setTitleGe(@NonNull String titleGe) {
        this.titleGe = titleGe;
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

    public String getTitle(Context context) {
        String language = LocaleHelper.getLanguage(context);
        if (language.equals(Locale.ENGLISH.getLanguage())) {
            return titleEn;
        } else {
            return titleGe;
        }
    }

    public String getText(Context context) {
        String language = LocaleHelper.getLanguage(context);
        if (language.equals(Locale.ENGLISH.getLanguage())) {
            return textEn;
        } else {
            return textGe;
        }
    }
}
