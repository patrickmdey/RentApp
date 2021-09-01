package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Article;

import java.util.List;

public interface ArticleDao {

    List<Article> filter(String name);

    Article create(String title, String description, Float pricePerDay, Integer idCategory, Integer idOwner);

}
