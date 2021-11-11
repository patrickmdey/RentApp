package ar.edu.itba.paw;

import ar.edu.itba.paw.interfaces.dao.ArticleDao;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Category;
import ar.edu.itba.paw.models.DBImage;
import ar.edu.itba.paw.models.OrderOptions;
import ar.edu.itba.paw.models.exceptions.CannotCreateArticleException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Sql("classpath:populateArticleTest.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Rollback(value = true)
public class ArticleDaoTest {
    @Autowired
    private ArticleDao articleDao;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void filterSucceedByName() {
        // Arrange
        final long[] expectedIds = {1};
        final String name = "Moto";
        final Long category = null;
        final OrderOptions orderBy = OrderOptions.LOWER_ARTICLE;
        final Long idUser = null;
        final Long location = null;
        final long page = 1L;

        // Act
        List<Article> result = articleDao.filter(name, category, orderBy, idUser, location, null, null, page);

        // Assert
        Assert.assertArrayEquals(expectedIds, result.stream().mapToLong(Article::getId).toArray());
    }

    @Test
    public void filterSucceedByCategory() {
        // Arrange
        final long[] expectedIds = {4, 1};
        final String name = null;
        final Long category = 1L;
        final OrderOptions orderBy = OrderOptions.LOWER_PRICE;
        final Long idUser = null;
        final Long location = null;
        final long page = 1L;

        // Act
        List<Article> result = articleDao.filter(name, category, orderBy, idUser, location, null, null, page);

        // Assert
        Assert.assertArrayEquals(expectedIds, result.stream().mapToLong(Article::getId).toArray());
    }

    @Test
    public void filterSucceedByUser() {
        // Arrange
        final long[] expectedIds = {4, 1, 3, 2};
        final String name = null;
        final Long category = null;
        final OrderOptions orderBy = OrderOptions.LOWER_PRICE;
        final Long idUser = 1L;
        final Long location = null;
        final long page = 1L;

        // Act
        List<Article> result = articleDao.filter(name, category, orderBy, idUser, location, null, null, page);

        // Assert
        Assert.assertArrayEquals(expectedIds, result.stream().mapToLong(Article::getId).toArray());
    }

    @Test
    public void filterSucceedByLocation() {
        // Arrange
        final long[] expectedIds = {4, 1, 3, 2};
        final String name = null;
        final Long category = null;
        final OrderOptions orderBy = OrderOptions.LOWER_PRICE;
        final Long idUser = null;
        final Long location = 20L;
        final long page = 1L;

        // Act
        List<Article> result = articleDao.filter(name, category, orderBy, idUser, location, null, null, page);

        // Assert
        Assert.assertArrayEquals(expectedIds, result.stream().mapToLong(Article::getId).toArray());
    }

    @Test
    public void findByIdSucceed() {
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
        Article r = optionalResult.get();

        Article result = em.find(Article.class, r.getId());

        Assert.assertEquals(title, result.getTitle());
        Assert.assertEquals(description, result.getDescription());
        Assert.assertEquals(pricePerDay, result.getPricePerDay());
        Assert.assertEquals(idOwner, result.getOwner().getId());

    }

    @Test
    public void findByIdFailArticleNotFound() {
        // Arrange
        final long idArticle = 9999;

        // Act
        Optional<Article> optionalResult = articleDao.findById(idArticle);

        // Assert
        Assert.assertFalse(optionalResult.isPresent());

    }

    @Test
    public void createArticleSucceed() {
        // Arrange
        final String title = "Moto";
        final String description = "moto para andar";
        final Float pricePerDay = 123F;
        final long idOwner = 1;

        // Act
        Article r = articleDao.createArticle(title, description, pricePerDay, idOwner);

        // Assert

        Article result = em.find(Article.class, r.getId());

        Assert.assertEquals(title, result.getTitle());
        Assert.assertEquals(description, result.getDescription());
        Assert.assertEquals(pricePerDay, result.getPricePerDay());
        Assert.assertEquals(idOwner, result.getOwner().getId());
    }

    @Test(expected = CannotCreateArticleException.class)
    public void createArticleFailNullValues() {
        // Arrange
        final String title = null;
        final String description = null;
        final float pricePerDay = 123;
        final long idOwner = 1;

        // Act
        articleDao.createArticle(title, description, pricePerDay, idOwner);

        // Assert
        Assert.fail();
    }

    @Test(expected = UserNotFoundException.class)
    public void createArticleFailOwnerNotFound() {
        // Arrange
        final String title = "Moto";
        final String description = "moto para andar";
        final Float pricePerDay = 123F;
        final long idOwner = 99999;

        // Act
        articleDao.createArticle(title, description, pricePerDay, idOwner);

        // Assert
        Assert.fail();
    }

    @Test
    public void rentedArticlesSucceed() {
        // Arrange
        final long idRenter = 2;
        final long page = 1;
        final long[] expectedIds = {3, 2};

        // Act
        List<Article> result = articleDao.rentedArticles(idRenter, page);

        // Assert
        Assert.assertArrayEquals(expectedIds, result.stream().mapToLong(Article::getId).toArray());

    }

    @Test
    public void timesRentedSucceed() {
        // Arrange
        final long articleId = 2;
        final Long timesRented = 1L;

        // Act
        Article article = articleDao.findById(articleId).get();
        Long result = article.getTimesRented();

        // Assert
        Assert.assertEquals(timesRented, result);
    }

    @Test
    public void ratingSucceed() {
        // Arrange
        final long idArticle = 2;
        final int expectedRating = 3;

        // Act
        Article article = articleDao.findById(idArticle).get();
        int result = article.getRating();

        // Assert
        Assert.assertEquals(expectedRating, result);
    }

    @Test
    public void timesReviewedSucceed() {
        // Arrange
        final long idArticle = 2;
        final long expectedTimesReviewed = 2;

        // Act
        Article article = articleDao.findById(idArticle).get();
        long result = article.getTimesReviewed();

        // Assert
        Assert.assertEquals(expectedTimesReviewed, result);
    }

    @Test
    public void categoriesSucceed() {
        // Arrange
        final long idArticle = 2;
        final Set<Category> expectedCategories = new HashSet<Category>();
        expectedCategories.add(new Category(3, "Category.Cars"));

        // Act
        Article article = articleDao.findById(idArticle).get();
        Set<Category> result = article.getCategories();

        // Assert
        Assert.assertEquals(expectedCategories, result);
    }

    @Test
    public void imagesSucceed() {
        // Arrange
        final long idArticle = 2;
        final int expectedImagesCount = 1;

        // Act
        Article article = articleDao.findById(idArticle).get();
        List<DBImage> result = article.getImages();

        // Assert
        Assert.assertEquals(expectedImagesCount, result.size());
    }

}
