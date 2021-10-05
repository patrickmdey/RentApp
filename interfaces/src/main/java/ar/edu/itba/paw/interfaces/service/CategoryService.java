package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.models.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> listCategories();

    Optional<Category> findById(Long category);
}
