package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Category;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    List<Article> get(String name);

    Optional<Article> create(String title, String description, Float pricePerDay, List<Category> categories, Integer idOwner);

    Optional<Article> findById(Integer articleId);
}
