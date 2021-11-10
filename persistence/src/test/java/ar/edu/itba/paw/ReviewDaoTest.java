package ar.edu.itba.paw;

import ar.edu.itba.paw.interfaces.dao.ReviewDao;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.exceptions.ArticleNotFoundException;
import ar.edu.itba.paw.models.exceptions.CannotCreateReviewException;
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
import java.util.Optional;

@Sql("classpath:populateReviewTest.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Rollback(value = true)
public class ReviewDaoTest {
    @Autowired
    private ReviewDao reviewDao;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void hasReviewedSucceed() {
        // Arrange
        final long userId = 2;
        final long articleId = 1;

        // Act
        boolean result = reviewDao.hasReviewed(userId, articleId);

        // Assert
        Assert.assertTrue(result);
    }

    @Test
    public void hasReviewedUserNotFound() {
        // Arrange
        final long userId = 200000;
        final long articleId = 1;

        // Act
        boolean result = reviewDao.hasReviewed(userId,articleId);

        // Assert
        Assert.assertFalse(result);
    }

    @Test
    public void hasReviewedArticleNotFound() {
        // Arrange
        final long userId = 2;
        final long articleId = 99999;

        // Act
        boolean result = reviewDao.hasReviewed(userId,articleId);

        // Assert
        Assert.assertFalse(result);
    }

    @Test
    public void createSucceed() {
        // Assert
        final int rating = 2;
        final String message = "bad";
        final long articleId = 1;
        final long userId = 2;

        // Act
        Review r = reviewDao.create(rating, message, articleId, userId);

        // Assert

        Review review = em.find(Review.class, r.getId());

        Assert.assertEquals(rating, review.getRating());
        Assert.assertEquals(message, review.getMessage());
        Assert.assertEquals(articleId, review.getArticle().getId());
        Assert.assertEquals(userId, review.getRenter().getId());
    }


    @Test(expected = CannotCreateReviewException.class)
    public void createFailInvalidParameters() {
        // Assert
        final int rating = -1;
        final String message = null;
        final long articleId = 1;
        final long userId = 2;

        // Act
        reviewDao.create(rating, message, articleId, userId);

        // Assert
        Assert.fail();
    }

    @Test(expected = ArticleNotFoundException.class)
    public void createFailArticleNotFound() {
        // Assert
        final int rating = 2;
        final String message = "bad";
        final long articleId = 9999;
        final long userId = 2;

        // Act
        reviewDao.create(rating, message, articleId, userId);

        // Assert
        Assert.fail();
    }

    @Test(expected = UserNotFoundException.class)
    public void createFailUserNotFound() {
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
    public void findByIdSucceed() {
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
    public void findByIdFailReviewNotFound() {
        // Assert
        final long reviewId = 9999;

        // Act
        Optional<Review> optionalReview = reviewDao.findById(reviewId);

        // Assert
        Assert.assertFalse(optionalReview.isPresent());
    }



}


















