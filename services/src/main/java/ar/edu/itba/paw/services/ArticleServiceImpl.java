package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.ArticleDao;
import ar.edu.itba.paw.interfaces.ArticleService;
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

    @Override
    public List<Article> filter(String name) {
        return articleDao.filter(name);
    }

    public List<Article> list() {
        return this.articleDao.list();
    }




    @Override
    public Article create(String title, String description, Float pricePerDay, Integer idCategory, Integer idOwner) {
        return articleDao.create(title,description, pricePerDay, idCategory, idOwner);
    }

    @Override
    public Optional<Article> findById(Integer articleId) {
        return articleDao.findById(articleId);
    }
}
