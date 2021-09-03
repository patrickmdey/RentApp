package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.ArticleDao;
import ar.edu.itba.paw.interfaces.ArticleService;
import ar.edu.itba.paw.interfaces.CategoryDao;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Category;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<Article> filter(String name) {
        return articleDao.filter(name);
    }


    private void appendCategories(Article article){
        article.setCategories(this.categoryDao.listByArticle(article.getId()));
    }

    @Override
    public List<Article> list() {
        List<Article> articles = this.articleDao.list();
        articles.forEach(this::appendCategories);
        return this.articleDao.list();
    }

    @Override
    public Optional<Article> findById(Integer articleId) {
        Optional<Article> toReturn = articleDao.findById(articleId);
        toReturn.ifPresent(this::appendCategories);
        return toReturn;
    }


    @Override
    public Article create(String title, String description, Float pricePerDay, Integer idCategory, Integer idOwner) {
        return articleDao.create(title,description, pricePerDay, idCategory, idOwner);
    }
}
