package ar.edu.itba.paw;

import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.persistence.ArticleDaoJdbc;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
import java.util.List;
import java.util.Optional;

@Sql("classpath:schema.sql")
@Sql("classpath:populateArticleTest.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ArticleDaoJdbcTest {
    @Autowired
    private ArticleDaoJdbc articleDao;
    @Autowired
    private DataSource dataSource;

    @Before
    public void setUp() {

    }

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
    public void filter_Succeed_ByName() {
        // Arrange
        final long[] expectedIds = {1};
        final String name = "Moto";
        final Long category = null;
        final String orderBy = "id";
        final Long idUser = null;
        final Long location = null;
        final Long page = 1L;

        // Act
        List<Article> result = articleDao.filter(name,category,orderBy,idUser,location,page);

        // Assert
        Assert.assertArrayEquals(expectedIds,result.stream().mapToLong(Article::getId).toArray());
    }

    @Test
    public void filter_Succeed_ByCategory() {
        // Arrange
        final long[] expectedIds = {1,4};
        final String name = null;
        final Long category = 1L;
        final String orderBy = "id";
        final Long idUser = null;
        final Long location = null;
        final Long page = 1L;

        // Act
        List<Article> result = articleDao.filter(name,category,orderBy,idUser,location,page);

        // Assert
        Assert.assertArrayEquals(expectedIds,result.stream().mapToLong(Article::getId).toArray());
    }

    @Test
    public void filter_Succeed_ByUser() {
        // Arrange
        final long[] expectedIds = {1,2,3,4};
        final String name = null;
        final Long category = null;
        final String orderBy = "id";
        final Long idUser = 1L;
        final Long location = null;
        final Long page = 1L;

        // Act
        List<Article> result = articleDao.filter(name,category,orderBy,idUser,location,page);

        // Assert
        Assert.assertArrayEquals(expectedIds,result.stream().mapToLong(Article::getId).toArray());
    }

    @Test
    public void filter_Succeed_ByLocation() {
        // Arrange
        final long[] expectedIds = {1,2,3,4};
        final String name = null;
        final Long category = null;
        final String orderBy = "id";
        final Long idUser = null;
        final Long location = 20L;
        final Long page = 1L;

        // Act
        List<Article> result = articleDao.filter(name,category,orderBy,idUser,location,page);

        // Assert
        Assert.assertArrayEquals(expectedIds,result.stream().mapToLong(Article::getId).toArray());
    }

    @Test
    public void findById_Succeed() {
        // Arrange
        final long idArticle = 1;
        final String title = "Moto";
        final String description = "moto para andar";
        final Float pricePerDay = 123F;
        final long idOwner = 1;

        // Act
        Optional<Article> optionalResult = articleDao.findById(idArticle);

        // Assert
        Assert.assertTrue(optionalResult.isPresent());
        Article result = optionalResult.get();

        Assert.assertEquals(title, result.getTitle());
        Assert.assertEquals(description, result.getDescription());
        Assert.assertEquals(pricePerDay, result.getPricePerDay());
        Assert.assertEquals(idOwner, result.getIdOwner());

    }

    @Test
    public void findById_Fail_ArticleNotFound() {
        // Arrange
        final long idArticle = 9999;

        // Act
        Optional<Article> optionalResult = articleDao.findById(idArticle);

        // Assert
        Assert.assertFalse(optionalResult.isPresent());

    }

    @Test
    public void createArticle_Succeed() {
        // Arrange
        final String title = "Moto";
        final String description = "moto para andar";
        final Float pricePerDay = 123F;
        final long idOwner = 1;

        // Act
        Optional<Article> optionalResult = articleDao.createArticle(title,description,pricePerDay,idOwner);

        // Assert
        Assert.assertTrue(optionalResult.isPresent());
        Article result = optionalResult.get();

        Assert.assertEquals(title, result.getTitle());
        Assert.assertEquals(description, result.getDescription());
        Assert.assertEquals(pricePerDay, result.getPricePerDay());
        Assert.assertEquals(idOwner, result.getIdOwner());
    }

    @Test(expected = DataAccessException.class)
    public void createArticle_Fail_NullValues() {
        // Arrange
        final String title = null;
        final String description = null;
        final Float pricePerDay = null;
        final long idOwner = 1;

        // Act
        articleDao.createArticle(title,description,pricePerDay,idOwner);

        // Assert
        Assert.fail();
    }

    @Test(expected = DataAccessException.class)
    public void createArticle_Fail_OwnerNotFound() {
        // Arrange
        final String title = "Moto";
        final String description = "moto para andar";
        final Float pricePerDay = 123F;
        final long idOwner = 99999;

        // Act
        articleDao.createArticle(title,description,pricePerDay,idOwner);

        // Assert
        Assert.fail();
    }

    @Test
    public void editArticle_Succeed() {
        // Arrange
        final long idArticle = 1;
        final String title = "Moto nueva";
        final String description = "super moto para andar";
        final Float pricePerDay = 500F;
        final int updatedRows = 1;

        // Act
        int result = articleDao.editArticle(idArticle,title,description,pricePerDay);

        // Assert
        Assert.assertEquals(updatedRows,result);
    }

    @Test(expected = DataAccessException.class)
    public void editArticle_Fail_NullValues() {
        // Arrange
        final long idArticle = 1;
        final String title = null;
        final String description = null;
        final Float pricePerDay = null;

        // Act
        articleDao.editArticle(idArticle,title,description,pricePerDay);

        // Assert
        Assert.fail();
    }

    @Test
    public void editArticle_Fail_ArticleNotFound() {
        // Arrange
        final long idArticle = 9999;
        final String title = "Moto nueva";
        final String description = "super moto para andar";
        final Float pricePerDay = 500F;
        final int updatedRows = 0;

        // Act
        int result = articleDao.editArticle(idArticle,title,description,pricePerDay);

        // Assert
        Assert.assertEquals(updatedRows,result);
    }

    @Test
    public void rentedArticles_Succeed() {
        // Arrange
        final long idRenter = 2;
        final long page = 1;
        final long[] expectedIds= {2,3};

        // Act
        List<Article> result = articleDao.rentedArticles(idRenter,page);

        // Assert
        Assert.assertArrayEquals(expectedIds,result.stream().mapToLong(Article::getId).toArray());

    }

    @Test
    public void timesRented_Succeed() {
        // Arrange
        final long articleId = 2;
        final Long timesRented = 1L;

        // Act
        Long result = articleDao.timesRented(articleId);

        // Assert
        Assert.assertEquals(timesRented, result);
    }

    @Test
    public void timesRented_Fail_ArticleNotFound() {
        // Arrange
        final long articleId = 999;
        final Long timesRented = 0L;

        // Act
        Long result = articleDao.timesRented(articleId);

        // Assert
        Assert.assertEquals(timesRented, result);
    }


}




















