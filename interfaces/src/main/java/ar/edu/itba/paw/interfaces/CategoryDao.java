package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryDao {
    //List<Category> filter(String name);

    List<Category> listByArticle(long articleId);

    List<Category> listAll();

    //Optional<Category> findById(long id);

}
