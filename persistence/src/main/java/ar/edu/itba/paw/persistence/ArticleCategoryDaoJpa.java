package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.ArticleCategoryDao;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Category;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ArticleCategoryDaoJpa implements ArticleCategoryDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Category> findFromArticle(long articleId) {
        return new ArrayList<>(em.find(Article.class, articleId).getCategories()); //TODO ver de cambiar
    }

    //TODO: ¿Deberia ser void? Si
    @Override
    public Long addToArticle(long articleId, Long category) {
        Article article = em.find(Article.class, articleId);
        article.getCategories().add(em.find(Category.class, category));
        return category;
    }

    @Override
    public void removeFromArticle(long articleId, Long category) {
        Article article = em.find(Article.class, articleId);
        article.getCategories().remove(em.find(Category.class, category));
    }
}
