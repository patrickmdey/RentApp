package ar.edu.itba.paw;

import ar.edu.itba.paw.interfaces.ReviewDao;
import ar.edu.itba.paw.models.Review;
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
import java.util.Optional;

@Sql("classpath:populateReviewTest.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ReviewDaoJdbcTest {
    @Autowired
    private ReviewDao reviewDao;
    @Autowired
    private DataSource dataSource;

    @After
    public void cleanUp(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        JdbcTestUtils.deleteFromTables(jdbcTemplate,
                "article",
                "account",
                "review"
        );
    }

    @Test
    public void getAverage_Succeed() {
        // Arrange
        final long articleId = 1;
        final float expectedAverage = 4;

        // Act
        float result = reviewDao.getAverage(articleId);

        // Assert
        Assert.assertEquals(expectedAverage,result,0.1);
    }

    @Test
    public void hasReviewed_Succeed() {
        // Arrange
        final long userId = 2;
        final long articleId = 1;

        // Act
        boolean result = reviewDao.hasReviewed(userId,articleId);

        // Assert
        Assert.assertTrue(result);
    }

    @Test
    public void hasReviewed_UserNotFound() {
        // Arrange
        final long userId = 200000;
        final long articleId = 1;

        // Act
        boolean result = reviewDao.hasReviewed(userId,articleId);

        // Assert
        Assert.assertFalse(result);
    }

    @Test
    public void hasReviewed_ArticleNotFound() {
        // Arrange
        final long userId = 2;
        final long articleId = 99999;

        // Act
        boolean result = reviewDao.hasReviewed(userId,articleId);

        // Assert
        Assert.assertFalse(result);
    }

    @Test
    public void create_Succeed() {
        // Assert
        final int rating = 2;
        final String message = "bad";
        final long articleId = 1;
        final long userId = 2;

        // Act
        Optional<Review> optionalReview = reviewDao.create(rating,message,articleId,userId);

        // Assert
        Assert.assertTrue(optionalReview.isPresent());
        Review review = optionalReview.get();

        Assert.assertEquals(rating,review.getRating());
        Assert.assertEquals(message,review.getMessage());
        Assert.assertEquals(articleId,review.getArticleId());
        Assert.assertEquals(userId,review.getRenterId());
    }

    @Test(expected = DataAccessException.class)
    public void create_Fail_ArticleNotFound() {
        // Assert
        final int rating = 2;
        final String message = "bad";
        final long articleId = 9999;
        final long userId = 2;

        // Act
        reviewDao.create(rating,message,articleId,userId);

        // Assert
        Assert.fail();
    }

    @Test(expected = DataAccessException.class)
    public void create_Fail_UserNotFound() {
        // Assert
        final int rating = 2;
        final String message = "bad";
        final long articleId = 1;
        final long userId = 9999;

        // Act
        reviewDao.create(rating,message,articleId,userId);

        // Assert
        Assert.fail();
    }

    @Test
    public void findById_Succeed() {
        // Assert
        final long reviewId = 1;

        // Act
        Optional<Review> optionalReview = reviewDao.findById(reviewId);

        // Assert
        Assert.assertTrue(optionalReview.isPresent());
        Review review = optionalReview.get();

        Assert.assertEquals(reviewId, review.getId());
    }

    @Test
    public void findById_Fail_ReviewNotFound() {
        // Assert
        final long reviewId = 9999;

        // Act
        Optional<Review> optionalReview = reviewDao.findById(reviewId);

        // Assert
        Assert.assertFalse(optionalReview.isPresent());
    }



}


















