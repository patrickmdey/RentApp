package ar.edu.itba.paw;

import ar.edu.itba.paw.interfaces.ArticleDao;
import ar.edu.itba.paw.interfaces.CategoryDao;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Category;
import ar.edu.itba.paw.models.User;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ArticlesDaoTest {
    @Autowired
    private DataSource ds;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private UserDao userDao;

    private JdbcTemplate jdbcTemplate;


    @After
    public void cleanUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "article");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "category");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "article_category");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "account");
    }


    private List<Article> populateDataBase(int elements) {
        List<Article> articles = new ArrayList<>(elements);

        for (int i = 0; i < elements; i++) {
            User owner = userDao.list().stream().findFirst().get();

            final String title = "Producto Titulo " + i;
            final String description = "Es un producto con una descripcion" + i;
            final float pricePerDay = 100 * i;
            final long idOwner = owner.getId();

            //TODO: Asi se hace?

            articles.add(articleDao.createArticle(
                    title,
                    description,
                    pricePerDay,
                    idOwner).get());
        }
        return articles;
    }

    @Test
    public void testCreate() {

        User owner = userDao.list().stream().findFirst().get();

        final String title = "Producto lindo";
        final String description = "Es un producto muy lindo";
        final float pricePerDay = 500;
        final long idOwner = owner.getId();
        final Article article = articleDao.createArticle(title, description, pricePerDay, idOwner).get();

        assertNotNull(article);
        assertEquals(article.getTitle(), title);
        assertEquals(article.getDescription(), description);
        assertEquals(article.getPricePerDay(), pricePerDay);
        assertEquals(article.getIdOwner(), idOwner);
    }

//    @Test
//    public void testList() {
//        List<Article> articlesDb = populateDataBase(4);
//
//        final List<Article> result = articleDao.();
//
//        assertNotNull(result);
//        assertEquals(result.size(), articlesDb.size());
//    }

    /*
    @Test
    public void testFilter() {
        final List<Article> articlesDb = populateDataBase(4);

        final List<Article> result = articleDao.filter("3", null, null, null, null);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(articlesDb.get(3), result.stream().findFirst().get());
    }

     */

    @Test
    public void testFindByIdExists() {
        final List<Article> articlesDb = populateDataBase(4);
        final Article expectedArticle = articlesDb.get(2);

        final Optional<Article> result = articleDao.findById(expectedArticle.getId());

        assertEquals(true, result.isPresent());
        assertEquals(expectedArticle, result.get());
    }

    @Test
    public void testFindByIdNotExists() {
        final List<Article> articlesDb = populateDataBase(4);

        final Optional<Article> result = articleDao.findById(900);

        assertEquals(false, result.isPresent());
    }

}
