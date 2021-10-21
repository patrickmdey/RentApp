package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.dao.ArticleCategoryDao;
import ar.edu.itba.paw.interfaces.dao.ArticleDao;
import ar.edu.itba.paw.interfaces.dao.ArticleImageDao;
import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.interfaces.service.ArticleService;
import ar.edu.itba.paw.interfaces.service.ImageService;
import ar.edu.itba.paw.interfaces.service.ReviewService;
import ar.edu.itba.paw.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private ArticleCategoryDao articleCategoryDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ArticleImageDao articleImageDao;

    /*
    private void appendCategories(Article article) {
        article.setCategories(this.articleCategoryDao.findFromArticle(article.getId()));
    }

    private void appendLocation(Article article) {
        Optional<User> owner = userDao.findById(article.getOwner().getId());
        owner.ifPresent(user -> article.setLocation(user.getLocation()));
    }

    private void appendImages(Article article) {
        List<Long> images = this.articleImageDao.findFromArticle(article.getId());
        article.setImages(images);
    }
     */
    private void appendRating(Article article) {
        article.setRating(reviewService.articleRating(article.getId()));
    }
    private void appendTimesRented(Article article) {
        article.setTimesRented(this.articleDao.timesRented(article.getId()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Article> get(String name, Long category, Long orderBy, Long user, Long location, long page) {
        List<Article> articles;

        OrderOptions orderOp = OrderOptions.LOWER_ARTICLE;
        if (orderBy != null && orderBy > 0 && orderBy < OrderOptions.values().length)
            orderOp = OrderOptions.values()[orderBy.intValue()];

        articles = this.articleDao.filter(name, category, orderOp, user, location, page);

        //articles.forEach(this::appendRating);
        appendInfo(articles);

        return articles;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Article> findById(long articleId) {
        Optional<Article> toReturn = articleDao.findById(articleId);

        if (toReturn.isPresent()) {
            //appendCategories(toReturn.get());
            //appendLocation(toReturn.get());
            //appendImages(toReturn.get());
            //appendInfo(toReturn.get());
            appendTimesRented(toReturn.get());
            appendRating(toReturn.get());
        }


        return toReturn;
    }

    @Override
    @Transactional(readOnly = true)
    public Long getMaxPage(String name, Long category, Long userId, Long location) {
        return articleDao.getMaxPage(name, category, userId, location);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getRentedMaxPage(Long renterId) {
        return articleDao.getRentedMaxPage(renterId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Article> recommendedArticles(long articleId) {
        List<Article> toReturn = articleDao.recommendedArticles(articleId);

        toReturn.forEach(article -> {
            //appendImages(article);
            //appendLocation(article);
            appendRating(article);
        });


        return toReturn;
    }


    @Override
    @Transactional
    public Article createArticle(String title, String description, Float pricePerDay, List<Long> categories, List<MultipartFile> images, long idOwner) {
        Article article = articleDao.createArticle(title, description, pricePerDay, idOwner);

        if (categories != null) {
            categories.forEach(catId -> articleCategoryDao.addToArticle(article.getId(), catId));
            //this.appendCategories(article);
        }
        images.forEach(image -> {
            DBImage img = imageService.create(image);
            articleImageDao.addToArticle(article, img);
        });
        return article;
    }

    @Override //TODO cambiarlo
    @Transactional
    public Optional<Article> editArticle(long id, String title, String description, Float pricePerDay, List<Long> categories) {
        articleDao.editArticle(id, title, description, pricePerDay);

        List<Long> old = articleCategoryDao.findFromArticle(id).stream().map(Category::getId).collect(Collectors.toList());
        List<Long> toRemove = new ArrayList<>(old);
        categories.forEach(c -> {
            if (!old.contains(c)) {
                articleCategoryDao.addToArticle(id, c);
            } else {
                toRemove.remove(c);
            }
        });
        toRemove.forEach(c -> articleCategoryDao.removeFromArticle(id, c));

        return articleDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Article> rentedArticles(long renterId, long page) {
        List<Article> articles = articleDao.rentedArticles(renterId, page);
        appendInfo(articles);
        return articleDao.rentedArticles(renterId, page);

//        appendInfo(articles);
        //return articles;
    }

    private void appendInfo(List<Article> articles) {
        articles.forEach(article -> {
//            appendCategories(article);
//            appendImages(article);
//            appendLocation(article);
            appendTimesRented(article);
            appendRating(article);
       });
    }
}
