package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Article;

import java.util.List;

public interface ArticlesDao {

    List<Article> filter(String name);

}
