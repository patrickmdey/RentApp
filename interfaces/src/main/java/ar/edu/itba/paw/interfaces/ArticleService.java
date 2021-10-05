package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Article;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    List<Article> get(String name, Long category, String orderBy, Long user, Long location, long page);

    Optional<Article> createArticle(String title, String description, Float pricePerDay, List<Long> categories, List<MultipartFile> image, long idOwner);

    Optional<Article> editArticle(long id, String title, String description, Float pricePerDay, List<Long> categories);

    Optional<Article> findById(long articleId);

    List<Article> rentedArticles(long renterId, long page);

    Long getMaxPage(String name, Long category, Long user, Long location);

    Long getRentedMaxPage(Long renterId);

    List<Article> recommendedArticles(long articleId);
}
