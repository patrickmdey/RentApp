package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.dao.CategoryDao;
import ar.edu.itba.paw.interfaces.service.ArticleService;
import ar.edu.itba.paw.interfaces.service.CategoryService;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Category;
import ar.edu.itba.paw.models.exceptions.ArticleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ArticleService articleService;

    @Override
    @Transactional(readOnly = true)
    public List<Category> listCategories(Integer articleId) {
        if(articleId == null)
            return categoryDao.listAll();

        return listCategoriesFromArticle(articleId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Category> findById(Long id) {
        if(id == null)
            return Optional.empty();
        
        return categoryDao.findById(id);
    }

    private List<Category> listCategoriesFromArticle(int articleId) {
        Article article = articleService.findById(articleId).orElseThrow(ArticleNotFoundException::new);
        return new ArrayList<>(article.getCategories());
    }
}
