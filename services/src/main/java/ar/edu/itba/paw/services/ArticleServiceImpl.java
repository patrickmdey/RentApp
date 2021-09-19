package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    private ArticleImageDao articleImageDao;

    private void appendCategories(Article article) {
        article.setCategories(this.articleCategoryDao.findFromArticle(article.getId()));
    }

    private void appendLocation(Article article) {
        Optional<User> owner = userDao.findById(article.getIdOwner());
        owner.ifPresent(user -> article.setLocation
                (Locations.values()[Math.toIntExact(user.getLocation())].getName()));
    }

    private void appendImages(Article article) {
        List<Long> images = this.articleImageDao.findFromArticle(article.getId());
        article.setImages(images);
    }

    @Override
    public List<Article> get(String name, Long category, String orderBy, Long user, Long location, Long page) {
        List<Article> articles;
        List<String> orderOptions = Arrays.stream(OrderOptions.values()).
                map(OrderOptions::getColumn).collect(Collectors.toList());

        if (!orderOptions.contains(orderBy)) // check orderBy is a valid value
            orderBy = null;

        articles = this.articleDao.filter(name, category, orderBy, user, location, page);


        articles.forEach(article -> {
            appendCategories(article);
            appendImages(article);
            appendLocation(article);
        });

        return articles;
    }

    @Override
    public Optional<Article> findById(long articleId) {
        Optional<Article> toReturn = articleDao.findById(articleId);
        if (toReturn.isPresent()) {
            appendCategories(toReturn.get());
            appendLocation(toReturn.get());
            appendImages(toReturn.get());
        }
        return toReturn;
    }

    @Override
    public Long getMaxPage(String name, Long category, Long user, Long location) {
        return articleDao.getMaxPage(name, category, user, location);
    }


    @Override
    public Optional<Article> createArticle(String title, String description, Float pricePerDay, List<Long> categories, List<MultipartFile> images, long idOwner) {

        Optional<Article> optArticle = articleDao.createArticle(title, description, pricePerDay, idOwner);

        if (optArticle.isPresent()) {
            Article article = optArticle.get();
            if (categories != null) {
                categories.forEach(cat_id -> articleCategoryDao.addToArticle(article.getId(), cat_id));
                this.appendCategories(article);
            }

            images.forEach(image -> {
                Optional<DBImage> img = imageService.create(image);
                img.ifPresent(dbImage -> articleImageDao.addToArticle(article.getId(), dbImage));
            });

            optArticle = Optional.of(article);
        }

        return optArticle;
    }

    @Override
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
    public List<Article> findByOwner(long ownerId) {
        List<Article> articles = articleDao.findByOwner(ownerId);
        articles.forEach(article -> {
            appendCategories(article);
            appendImages(article);
            appendLocation(article);
        });
        return articles;
    }
}
