package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.models.DBImage;

import java.util.List;

public interface ArticleImageDao {

    DBImage addToArticle(long articleId, DBImage image);

    List<Long> findFromArticle(long articleId);

}
