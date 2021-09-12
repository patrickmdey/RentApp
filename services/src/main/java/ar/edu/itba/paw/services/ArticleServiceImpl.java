package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private ArticleImageDao articleImageDao;

    private void appendCategories(Article article) {
        article.setCategories(this.articleCategoryDao.findFromArticle(article.getId()));
    }

    private void appendLocation(Article article) {
        Optional<User> owner = userDao.findById(article.getIdOwner());
        owner.ifPresent(user -> article.setLocation(user.getLocation()));
    }

    private void appendImages(Article article) {
        List<Long> images = this.articleImageDao.findFromArticle(article.getId());
        article.setImages(images);
    }

    @Override
    public List<Article> get(String name, Long category, String orderBy) {
        List<Article> articles;
        List<String> orderOptions = Arrays.stream(OrderOptions.values()).
                map(OrderOptions::getColumn).collect(Collectors.toList());

        if (!orderOptions.contains(orderBy)) // check orderBy is a valid value
            orderBy = null;

        if (name == null && category == null && orderBy == null) {
            articles = this.articleDao.list();
        } else {
            articles = this.articleDao.filter(name, category, orderBy);
        }

        articles.forEach(this::appendCategories);
        articles.forEach(this::appendLocation);
        articles.forEach(this::appendImages);

        return articles;
    }

    @Override
    public Optional<Article> findById(Integer articleId) {
        Optional<Article> toReturn = articleDao.findById(articleId);
        toReturn.ifPresent(this::appendCategories);
        toReturn.ifPresent(this::appendLocation);
        return toReturn;
    }


    @Override
    public Optional<Article> createArticle(String title, String description, Float pricePerDay, List<Category> categories, MultipartFile image, long idOwner) {

        Optional<Article> optArticle = articleDao.createArticle(title, description, pricePerDay, idOwner);

        if (optArticle.isPresent()) {
            Article article = optArticle.get();
            if (categories != null) {
                categories.forEach(t -> articleCategoryDao.addToArticle(article.getId(), t));
                article.setCategories(categories);
            }
            optArticle = Optional.of(article);

            Optional<DBImage> img = imageService.create(image);
            img.ifPresent(dbImage -> articleImageDao.addToArticle(article.getId(), dbImage));
        }

        return optArticle;
    }
}
