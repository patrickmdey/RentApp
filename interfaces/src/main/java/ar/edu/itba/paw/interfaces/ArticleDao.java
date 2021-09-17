package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleDao {

    List<Article> filter(String name, Long category, String orderBy, Long user, Long location, Long page);

    Optional<Article> findById(long id);

    List<Article> findByOwner(long ownerId);

    Optional<Article> createArticle(String title, String description, Float pricePerDay, long idOwner);

    Long getMaxPage();


}
