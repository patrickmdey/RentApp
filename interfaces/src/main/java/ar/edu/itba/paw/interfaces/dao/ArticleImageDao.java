package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.DBImage;

import java.util.List;

public interface ArticleImageDao {

    DBImage addToArticle(Article article, DBImage image);


    List<Long> findFromArticle(long articleId);

}
