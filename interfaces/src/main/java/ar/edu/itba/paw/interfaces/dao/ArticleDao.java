package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Locations;
import ar.edu.itba.paw.models.OrderOptions;

import java.util.List;
import java.util.Optional;

public interface ArticleDao {

    List<Article> filter(String name, Long category, OrderOptions orderBy, Long user, Long location, Float initPrice, Float endPrice, long page);

    List<Article> rentedArticles(long renterId, long page);

    Optional<Article> findById(long id);

    Article createArticle(String title, String description, float pricePerDay, long idOwner);

    Long getMaxPage(String name, Long category, Long user, Long location, Float initPrice, Float endPrice);

    Long getRentedMaxPage(long user);

    List<Article> recommendedArticles(long articleId);

    List<Locations> getUsedLocations();
}
