package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.ArticleImageDao;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.DBImage;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Primary
public class ArticleImageDaoJpa implements ArticleImageDao {

    // TODO: borrar clase (casi no se usa)

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public DBImage addToArticle(Article article, DBImage image) {
        article.addImage(image);
        return image;
    }

    @Override
    public List<Long> findFromArticle(long articleId) {
        return em.find(Article.class, articleId).getImages().stream()
                .map(DBImage::getId).collect(Collectors.toList());
    }
}
