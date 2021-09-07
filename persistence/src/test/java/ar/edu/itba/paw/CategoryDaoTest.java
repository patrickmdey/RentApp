package ar.edu.itba.paw;


import ar.edu.itba.paw.interfaces.ArticleDao;
import ar.edu.itba.paw.interfaces.CategoryDao;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Category;
import ar.edu.itba.paw.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertArrayEquals;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CategoryDaoTest {
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
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"article");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"category");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"article_category");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"account");
    }

    private List<Article> populatedArticles = new ArrayList<>();

    @Before
    public void populateDb() {
        final User owner = userDao.list().stream().findFirst().get();
        final List<Category> allCategories = categoryDao.listAll();

        for (int i = 0 ; i < 5; i++){

            final String title = "Producto Titulo " + i;
            final String description = "Es un producto con una descripcion" + i;
            final float pricePerDay = 100 * i;
            final Long idOwner = owner.getId();
            Collections.shuffle(allCategories);
            final List<Category> selectedCategories= new ArrayList<>();

            allCategories.subList(0,2).forEach(t->selectedCategories.add(t));

            //TODO: Asi se hace?

            populatedArticles.add(articleDao.create(title, description, pricePerDay, selectedCategories,idOwner)
                                            .get()
                                 );
        }
    }

    @Test
    public void testListByExistingArticleId(){
        final Article article = populatedArticles.get(new Random().nextInt(populatedArticles.size()));
        final List<Category> expectedCategories = article.getCategories();

        final List<Category> result = categoryDao.listByArticle(article.getId());

        assertEquals(new HashSet<>(expectedCategories),
                new HashSet<>(result));
    }

    @Test
    public void testListByNonExistingArticleId(){
        final List<Category> result = categoryDao.listByArticle(45654);

        assertEquals(0,result.size());
    }
}
