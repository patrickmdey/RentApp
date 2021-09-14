package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.DBImage;

import java.util.List;

public interface ArticleImageDao {

    public DBImage addToArticle(long articleId, DBImage image);

    public List<Long> findFromArticle(long articleId);

}
