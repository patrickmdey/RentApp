package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    List<Article> get(String name, Long category, String orderBy);

    Optional<Article> createArticle(String title, String description, Float pricePerDay, long idOwner);

    Optional<Article> findById(Integer articleId);
}
