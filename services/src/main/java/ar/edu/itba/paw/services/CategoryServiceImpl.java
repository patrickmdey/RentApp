package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.CategoryDao;
import ar.edu.itba.paw.interfaces.CategoryService;
import ar.edu.itba.paw.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDao categoryDao;

    @Override
    public List<Category> listCategories() {
        return categoryDao.listCategories();
    }
}
