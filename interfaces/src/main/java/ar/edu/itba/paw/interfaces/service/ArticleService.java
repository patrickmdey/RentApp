package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Locations;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    List<Article> get(String name, Long category, Long orderBy, Long user, Long location, long page);

    Article createArticle(String title, String description, float pricePerDay, List<Long> categories, List<MultipartFile> image, long idOwner);

    Optional<Article> editArticle(long id, String title, String description, float pricePerDay, List<Long> categories);

    Optional<Article> findById(long articleId);

    List<Article> rentedArticles(long renterId, long page);

    Long getMaxPage(String name, Long category, Long user, Long location);

    Long getRentedMaxPage(long renterId);

    List<Article> recommendedArticles(long articleId);

    List<Locations> getUsedLocations();
}
