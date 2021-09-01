package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface ArticleDao {

    List<Article> filter(String name);

    List<Article> list();

    Optional<Article> findById(long id);

    Article create(String title, String description, Float pricePerDay, Integer idCategory, Integer idOwner);

}
