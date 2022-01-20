package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Locations;
import java.util.List;
import java.util.Optional;

public interface ArticleService {
    List<Article> get(String name, Long category, Long orderBy, Long user, Long location, Float initPrice,
                      Float endPrice, long page);

    Article createArticle(String title, String description, float pricePerDay, List<Long> categories, List<byte[]> image, long idOwner);

    Optional<Article> editArticle(long id, String title, String description, float pricePerDay, List<Long> categories);

    Optional<Article> findById(long articleId);

    List<Article> rentedArticles(long renterId, long page);

    long getMaxPage(String name, Long category, Long user, Long location, Float initPrice, Float endPrice);

    long getRentedMaxPage(long renterId);

    List<Article> recommendedArticles(long articleId);

    List<Locations> getUsedLocations();
}
