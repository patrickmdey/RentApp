package ar.edu.itba.paw;

import ar.edu.itba.paw.models.DBImage;
import ar.edu.itba.paw.persistence.ArticleImageDaoJdbc;
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

@Sql("classpath:schema.sql")
@Sql("classpath:populateArticleImageDaoTest.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ArticleImageDaoJdbcTest {
    @Autowired
    private ArticleImageDaoJdbc articleImageDao;
    @Autowired
    private DataSource dataSource;

    @Before
    public void setUp() {

    }

    @After
    public void cleanUp() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        JdbcTestUtils.deleteFromTables(jdbcTemplate,
                "article",
                "picture",
                "article_picture",
                "account"
        );
    }

    @Test
    public void findFromArticle_Succeed(){
        // Arrange
        final long idArticle = 1;
        final Long[] expectedIds = {1L};

        // Act
        List<Long> result = articleImageDao.findFromArticle(idArticle);

        // Assert
        Assert.assertArrayEquals(expectedIds,result.toArray());
    }

    @Test
    public void findFromArticle_Fail_ArticleNotFound(){
        // Arrange
        final long idArticle = 999;
        final Long[] expectedIds = {};

        // Act
        List<Long> result = articleImageDao.findFromArticle(idArticle);

        // Assert
        Assert.assertArrayEquals(expectedIds,result.toArray());
    }

    @Test
    public void addToArticle_Succeed() {
        // Arrange
        final long idArticle = 1;
        final DBImage image = new DBImage(2,new byte[]{} );

        // Act
        final DBImage result = articleImageDao.addToArticle(idArticle,image);

        // Assert
        Assert.assertEquals(image,result);

    }

    @Test(expected = NullPointerException.class)
    public void addToArticle_Fail_NullValues() {
        // Arrange
        final long idArticle = 1;
        final DBImage image = null;

        // Act
        final DBImage result = articleImageDao.addToArticle(idArticle,image);

        // Assert
        Assert.fail();
    }

    @Test(expected = DataAccessException.class)
    public void addToArticle_Fail_ImageAlreadyAdded() {
        // Arrange
        final long idArticle = 1;
        final DBImage image = new DBImage(1,new byte[]{} );

        // Act
        final DBImage result = articleImageDao.addToArticle(idArticle,image);

        // Assert
        Assert.fail();
    }

    @Test(expected = DataAccessException.class)
    public void addToArticle_Fail_ImageNotFound() {
        // Arrange
        final long idArticle = 1;
        final DBImage image = new DBImage(999,new byte[]{} );

        // Act
        final DBImage result = articleImageDao.addToArticle(idArticle,image);

        // Assert
        Assert.fail();
    }

    @Test(expected = DataAccessException.class)
    public void addToArticle_Fail_ArticleNotFound() {
        // Arrange
        final long idArticle = 9999;
        final DBImage image = new DBImage(2,new byte[]{} );

        // Act
        final DBImage result = articleImageDao.addToArticle(idArticle,image);

        // Assert
        Assert.fail();
    }
}
