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
public interface ArticleCategoryDao {
    @Query("SELECT * FROM article_category")
    List<ArticleCategory> getAll();

    @Query("SELECT * FROM article_category where category_id = (:categoryId)")
    ArticleCategory getByCategoryId(int categoryId);

    @Query("SELECT * FROM article_category where article_id = (:articleId)")
    ArticleCategory getByArticleId(int articleId);

    @Insert
    void insert(ArticleCategory articleCategory);

    @Delete
    void delete(ArticleCategory articleCategory);
}
