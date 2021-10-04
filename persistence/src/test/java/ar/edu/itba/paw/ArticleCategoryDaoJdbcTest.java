package ar.edu.itba.paw;

import ar.edu.itba.paw.models.Category;
import ar.edu.itba.paw.persistence.ArticleCategoryDaoJdbc;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

@Sql("classpath:populateArticleCategoryTest.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ArticleCategoryDaoJdbcTest {

    @Autowired
    private ArticleCategoryDaoJdbc articleCategoryDao;
    @Autowired
    private DataSource dataSource;

    @After
    public void cleanUp(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        JdbcTestUtils.deleteFromTables(jdbcTemplate,
                "article",
                "category",
                "article_category",
                "account"
                );
    }

    @Test
    public void findFromArticle_Succeed() {
        // Arrange
        final long articleId = 1;
        final List<Category> expectedCategories = Arrays.asList(
                new Category(1, "Category.Technology"),
                new Category(2, "Category.Camping"),
                new Category(6, "Category.travel")
        );

        // Act
        List<Category> result = articleCategoryDao.findFromArticle(articleId);

        // Assert
        Assert.assertArrayEquals(expectedCategories.toArray(),result.toArray());
    }

    @Test
    public void addToArticle_Succeed() {
        // Arrange
        final long articleId = 1;
        final long categoryId = 3;

        // Act
        final long resultCategory = articleCategoryDao.addToArticle(articleId,categoryId);

        // Assert
        Assert.assertEquals(categoryId,resultCategory);
    }

    @Test(expected = DataAccessException.class)
    public void addToArticle_Fail_ArticleNotFound() {
        // Arrange
        final long articleId = 999;
        final long categoryId = 3;

        // Act
        articleCategoryDao.addToArticle(articleId,categoryId);

        // Assert
        Assert.fail();
    }

    @Test(expected = DataAccessException.class)
    public void addToArticle_Fail_CategoryNotFound() {
        // Arrange
        final long articleId = 1;
        final long categoryId = 999;

        // Act
        articleCategoryDao.addToArticle(articleId,categoryId);

        // Assert
        Assert.fail();
    }

    @Test(expected = DataAccessException.class)
    public void addToArticle_Fail_ArticleHasCategory() {
        // Arrange
        final long articleId = 1;
        final long categoryId = 999;

        // Act
        articleCategoryDao.addToArticle(articleId,categoryId);

        // Assert
        Assert.fail();
    }

    @Test(expected = Test.None.class)
    public void removeFromArticle_Succeed() {
        // Arrange
        final long articleId = 1;
        final long categoryId = 3;

        // Act
        articleCategoryDao.removeFromArticle(articleId,categoryId);

        // Assert
    }
    // TODO: Is there a way to test fail cases? like article does not exist?
    //       the function and SQL won't throw an error



}
