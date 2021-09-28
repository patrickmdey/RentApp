package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Category;

import java.util.List;

public interface ArticleCategoryDao {

    List<Category> findFromArticle(long articleId);

    Long addToArticle(long articleId, Long category);

    void removeFromArticle(long id, Long c);
}
