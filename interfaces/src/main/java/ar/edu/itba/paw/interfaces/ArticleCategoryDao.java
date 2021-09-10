package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Category;

import java.util.List;

public interface ArticleCategoryDao {

    public List<Category> findFromArticle(long articleId);

    public Category addToArticle(long articleId, Category category);
}
