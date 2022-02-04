package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.models.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryDao {

    List<Category> listAll();

    Optional<Category> findById(long category);

}
