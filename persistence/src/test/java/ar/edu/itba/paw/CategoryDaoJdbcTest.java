package ar.edu.itba.paw;

import ar.edu.itba.paw.interfaces.dao.CategoryDao;
import ar.edu.itba.paw.models.Category;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Sql("classpath:populateCategoryTest.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CategoryDaoJdbcTest {

    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private DataSource dataSource;

    @After
    public void cleanUp(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        JdbcTestUtils.deleteFromTables(jdbcTemplate,
                "category"
        );
    }

    @Test
    public void listAllSucceed() {
        // Arrange
        final long[] expectedIds = {1,2,3,4,5,6,7};

        // Act
        final List<Category> result = categoryDao.listAll();

        // Assert
        Assert.assertArrayEquals(expectedIds,result.stream().mapToLong(Category::getId).toArray());

    }

    @Test
    public void findByIdSucceed() {
        // Arrange
        final long idCategory = 2;

        // Act
        Optional<Category> optionalResult = categoryDao.findById(idCategory);

        // Assert
        Assert.assertTrue(optionalResult.isPresent());
        Category result = optionalResult.get();

        Assert.assertEquals(idCategory,result.getId());
    }

    @Test
    public void findByIdFailCategoryNotFound() {
        // Arrange
        final long idCategory = 2000;

        // Act
        Optional<Category> optionalResult = categoryDao.findById(idCategory);

        // Assert
        Assert.assertFalse(optionalResult.isPresent());

    }
}
