package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.ArticleDao;
import ar.edu.itba.paw.interfaces.ArticleService;
import ar.edu.itba.paw.interfaces.CategoryDao;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Category;
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

    @Autowired
    private UserDao userDao;

    private List<Article> filter(String name) {
        return this.articleDao.filter(name);
    }

    private void appendCategories(Article article) {
        article.setCategories(this.categoryDao.listByArticle(article.getId()));
    }

    private List<Article> list() {
        List<Article> articles = this.articleDao.list();
        articles.forEach(this::appendCategories);
        return this.articleDao.list();
    }

    @Override
    public List<Article> get(String name) {
        if (name == null)
            return this.list();

        return this.filter(name);
    }

    @Override
    public Optional<Article> findById(Integer articleId) {
        Optional<Article> toReturn = articleDao.findById(articleId);
        toReturn.ifPresent(this::appendCategories);
        return toReturn;
    }


    @Override
    public Article create(String title, String description, Float pricePerDay, List<Category> categories, Long idOwner) {
        return articleDao.create(title, description, pricePerDay,categories, idOwner);
    }
}
