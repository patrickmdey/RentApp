package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Locations;
import ar.edu.itba.paw.models.OrderOptions;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    List<Article> get(String name, Long category, OrderOptions orderBy, Long user, Locations location, Float initPrice,
                      Float endPrice, Long renterId, long page);

    Article createArticle(String title, String description, float pricePerDay, List<Long> categories, List<byte[]> image, long idOwner);

    Optional<Article> editArticle(long id, String title, String description, float pricePerDay, List<Long> categories);

    Optional<Article> findById(long articleId);

    long getMaxPage(String name, Long category, Long user, Locations location, Float initPrice, Float endPrice, Long renterId);

    List<Article> recommendedArticles(long articleId);

    List<Locations> getUsedLocations();
}
