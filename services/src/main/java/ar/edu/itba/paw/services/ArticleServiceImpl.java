package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.ArticleDao;
import ar.edu.itba.paw.interfaces.ArticleService;
import ar.edu.itba.paw.interfaces.CategoryDao;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.Article;
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

    @Autowired
    private UserDao userDao;

    private List<Article> filter(String name, Long category, String orderBy) {
        return this.articleDao.filter(name, category, orderBy);
    }

    private void appendCategories(Article article) {
        article.setCategories(this.categoryDao.listByArticle(article.getId()));
    }

    private void appendLocation(Article article) {
        Optional<User> owner = userDao.findById(article.getIdOwner());
        owner.ifPresent(user -> article.setLocation(user.getLocation()));
    }

    private List<Article> list() {
        List<Article> articles = this.articleDao.list();
        articles.forEach(this::appendCategories);
        articles.forEach(this::appendLocation);
        return articles;
    }

    @Override
    public List<Article> get(String name, Long category, String orderBy) {

        if (name == null && category == null && orderBy == null)
            return this.list();

        return this.filter(name, category, orderBy);
    }

    @Override
    public Optional<Article> findById(Integer articleId) {
        Optional<Article> toReturn = articleDao.findById(articleId);
        toReturn.ifPresent(this::appendCategories);
        toReturn.ifPresent(this::appendLocation);
        return toReturn;
    }


    @Override
    public Optional<Article> createArticle(String title, String description, Float pricePerDay, long idOwner) {
        return articleDao.createArticle(title, description, pricePerDay, idOwner);
    }
}
