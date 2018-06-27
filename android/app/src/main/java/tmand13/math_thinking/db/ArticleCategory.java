package tmand13.math_thinking.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by tmand on 6/27/2018.
 */

@Entity(tableName = "article_category", foreignKeys = {
        @ForeignKey(
                entity = Article.class,
                parentColumns = "article_id",
                childColumns = "article_id"
        ),
        @ForeignKey(
                entity = Category.class,
                parentColumns = "category_id",
                childColumns = "category_id"
        )})
public class ArticleCategory {
    @PrimaryKey
    @ColumnInfo(name = "article_category_id")
    private int articleCategoryId;

    @ColumnInfo(name = "article_id")
    private int articleId;

    @ColumnInfo(name = "category_id")
    private int categoryId;

    public ArticleCategory(int articleCategoryId, int articleId, int categoryId) {
        this.articleCategoryId = articleCategoryId;
        this.articleId = articleId;
        this.categoryId = categoryId;
    }

    public int getArticleCategoryId() {
        return articleCategoryId;
    }

    public void setArticleCategoryId(int articleCategoryId) {
        this.articleCategoryId = articleCategoryId;
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
}
