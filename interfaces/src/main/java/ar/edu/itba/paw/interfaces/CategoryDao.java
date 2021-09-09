package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryDao {
    //List<Category> filter(String name);

    List<String> listByArticle(long articleId);

    List<Category> listCategories();

    //Optional<Category> findById(long id);

}
