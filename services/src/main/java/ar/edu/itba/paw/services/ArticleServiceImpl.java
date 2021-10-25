package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.dao.*;
import ar.edu.itba.paw.interfaces.service.ArticleService;
import ar.edu.itba.paw.interfaces.service.ImageService;
import ar.edu.itba.paw.interfaces.service.ReviewService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.ArticleNotFoundException;
import ar.edu.itba.paw.models.exceptions.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ReviewService reviewService;

    private void appendRating(Article article) {
        article.setRating(reviewService.articleRating(article.getId()));
    }

    private void appendTimesRented(Article article) {
        article.setTimesRented(this.articleDao.timesRented(article.getId()));
    }

    private void appendTimesReviewed(Article article) {
        article.setTimesReviewed(reviewService.timesReviewed(article.getId()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Article> get(String name, Long category, Long orderBy, Long user, Long location, long page) {
        List<Article> articles;

        OrderOptions orderOp = OrderOptions.LOWER_ARTICLE;
        if (orderBy != null && orderBy > 0 && orderBy < OrderOptions.values().length)
            orderOp = OrderOptions.values()[orderBy.intValue()];

        articles = this.articleDao.filter(name, category, orderOp, user, location, page);

        appendInfo(articles);

        return articles;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Article> findById(long articleId) {
        Optional<Article> toReturn = articleDao.findById(articleId);

        if (toReturn.isPresent()) {
            appendTimesRented(toReturn.get());
            appendRating(toReturn.get());
            appendTimesReviewed(toReturn.get());
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

        toReturn.forEach(this::appendRating);
        return toReturn;
    }


    @Override
    @Transactional
    public Article createArticle(String title, String description, Float pricePerDay, List<Long> categories, List<MultipartFile> images, long idOwner) {
        Article article = articleDao.createArticle(title, description, pricePerDay, idOwner);

        if (categories != null) {
            article.setCategories(categories.stream().map(c -> categoryDao.findById(c)
                    .orElseThrow(CategoryNotFoundException::new)).collect(Collectors.toSet()));
        }
        images.forEach(image -> {
            DBImage img = imageService.create(image);
            article.addImage(img);
        });
        return article;
    }

    @Override
    @Transactional
    public Optional<Article> editArticle(long id, String title, String description, Float pricePerDay, List<Long> categories) {
        Article article = articleDao.findById(id).orElseThrow(ArticleNotFoundException::new);
        article.setTitle(title);
        article.setDescription(description);
        article.setPricePerDay(pricePerDay);

        article.setCategories(categories.stream().map(c -> categoryDao.findById(c)
                        .orElseThrow(CategoryNotFoundException::new)).collect(Collectors.toSet()));

        return Optional.of(article);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Article> rentedArticles(long renterId, long page) {
        List<Article> articles = articleDao.rentedArticles(renterId, page);
        appendInfo(articles);
        return articles;
    }

    private void appendInfo(List<Article> articles) {
        articles.forEach(article -> {
            appendTimesRented(article);
            appendRating(article);
            appendTimesReviewed(article);
       });
    }
}
