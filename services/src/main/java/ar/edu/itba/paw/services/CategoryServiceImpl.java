package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.dao.CategoryDao;
import ar.edu.itba.paw.interfaces.service.CategoryService;
import ar.edu.itba.paw.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    @Transactional(readOnly = true)
    public List<Category> listCategories() {
        return categoryDao.listAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Category> findById(Long id) {
        if(id == null)
            return Optional.empty();
        
        return categoryDao.findById(id);
    }
}
