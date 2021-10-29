package ar.edu.itba.paw;

import ar.edu.itba.paw.interfaces.dao.CategoryDao;
import ar.edu.itba.paw.models.Category;
import ar.edu.itba.paw.models.exceptions.CategoryNotFoundException;
import ar.edu.itba.paw.services.CategoryServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceImplTest {
    @InjectMocks
    private CategoryServiceImpl categoryService = new CategoryServiceImpl();

    @Mock
    private CategoryDao categoryDao;

    @Before
    public void setUp(){
        this.categories = Arrays.asList(
                new Category(1,"Turismo"),
                new Category(2,"Autos"),
                new Category(3,"Herramientas"),
                new Category(4,"Vacaciones"),
                new Category(5,"Trabajo")
        );
    }

    private List<Category> categories;

    @Test
    public void findByIdSucceed(){
        // Arrange
        long categoryId = 3;
        Category expectedCategory = this.categories.get((int) categoryId);

        when(categoryDao.findById(eq(categoryId)))
                .thenReturn(Optional.of(expectedCategory));

        // Act
        Optional<Category> optionalCategory = categoryService.findById(categoryId);

        // Assert
        Assert.assertTrue(optionalCategory.isPresent());
        Category category = optionalCategory.get();

        Assert.assertEquals(expectedCategory,category);
    }

    @Test
    public void findByIdFailIdIsNull(){
        // Arrange
        Long categoryId = null;

        // Act
        Optional<Category> optionalCategory = categoryService.findById(categoryId);

        // Assert
        Assert.assertFalse(optionalCategory.isPresent());

    }

    @Test(expected = CategoryNotFoundException.class)
    public void findByIdFailsCategoryNotFound(){
        // Arrange
        long categoryId = 3;
        Category expectedCategory = this.categories.get((int) categoryId);

        when(categoryDao.findById(eq(categoryId)))
                .thenThrow(CategoryNotFoundException.class);

        // Act
        Optional<Category> optionalCategory = categoryService.findById(categoryId);

        // Assert
        Assert.fail();
    }

}
