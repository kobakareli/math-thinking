package tmand13.math_thinking.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by tmand on 4/17/2018.
 */

@Dao
public interface ArticleDao {
    @Query("SELECT * FROM article")
    List<Article> getAll();

    @Query("SELECT * FROM article WHERE article_id = (:articleId)")
    Article getArticle(int articleId);

    @Insert
    void insert(Article article);

    @Insert
    void insertAll(Article... articles);

    @Delete
    void delete(Article article);

    @Query("SELECT COUNT(article_id) FROM article WHERE article_id = (:articleId)")
    int contains(int articleId);
}
