package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Category;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface ArticleDao {

    List<Article> filter(String name, Long category, String orderBy, Long user, Long location, Long page);

    Optional<Article> findById(long id);

    Optional<Article> createArticle(String title, String description, Float pricePerDay, long idOwner);

    Long getMaxPage();


}
