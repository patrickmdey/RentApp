package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Category;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    List<Article> get(String name, Long category, String orderBy, Long user, Long location, Long page);

    Optional<Article> createArticle(String title, String description, Float pricePerDay, List<Long> categories, List<MultipartFile> image, long idOwner);

    Optional<Article> findById(Integer articleId);

    Long getMaxPage(String name, Long category, Long user, Long location);
}
