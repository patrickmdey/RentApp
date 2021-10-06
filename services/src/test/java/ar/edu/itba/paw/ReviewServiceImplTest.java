package ar.edu.itba.paw;

import ar.edu.itba.paw.interfaces.dao.ReviewDao;
import ar.edu.itba.paw.interfaces.service.ArticleService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.services.ReviewServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReviewServiceImplTest {

    @InjectMocks
    private ReviewServiceImpl reviewService = new ReviewServiceImpl();

    @Mock
    private ArticleService articleService;
    @Mock
    private ReviewDao reviewDao;
    @Mock
    private UserService userService;

    @Before
    public void setUp() throws ParseException {
        this.userOwner = new User(1,"owner@mail.com","password","owner",
                "owner", Locations.values()[3],null, UserType.OWNER);
        this.userRenter = new User(2,"renter@mail.com","password","renter",
                "renter",Locations.values()[5],null,UserType.RENTER);

        this.article = new Article(123,"bike", "fast bike", 400F,userOwner.getId());

        this.review = new Review(
                786,
                "Good product",
                article.getId(),
                userRenter.getId(),
                new Date(System.currentTimeMillis())
        );
    }

    private User userOwner;
    private User userRenter;
    private Article article;
    private Review review;

    @Test
    public void testCreate() {
        // Arrange
        when(articleService.findById(eq(review.getArticleId()))).thenReturn(Optional.of(article));
        when(userService.findById(eq(review.getRenterId()))).thenReturn(Optional.of(userRenter));
        when(reviewDao.create(
                eq(review.getRating()),
                eq(review.getMessage()),
                eq(review.getArticleId()),
                eq(review.getRenterId())
        )).thenReturn(review);

        // Act
        Review result = reviewService.create(review.getRating(),review.getMessage(),review.getArticleId(), review.getRenterId());

        // Assert
        Assert.assertEquals(review.getMessage(), result.getMessage());
        Assert.assertEquals(review.getRating(), result.getRating());
        Assert.assertEquals(review.getArticleId(), result.getArticleId());
        Assert.assertEquals(review.getRenterId(), result.getRenterId());
    }

    @Test(expected = RuntimeException.class)
    public void testCreateFail() {
        // Arrange
        when(articleService.findById(eq(review.getArticleId()))).thenReturn(Optional.of(article));
        when(reviewDao.create(
                eq(review.getRating()),
                eq(review.getMessage()),
                eq(review.getArticleId()),
                eq(review.getRenterId())
        )).thenThrow(RuntimeException.class);

        // Act
        reviewService.create(review.getRating(),review.getMessage(),review.getArticleId(), review.getRenterId());

        // Assert
        Assert.fail();
    }

    @Test(expected = RuntimeException.class)
    public void testCreateFailUserNotFound() {
        // Arrange
        when(articleService.findById(eq(review.getArticleId()))).thenReturn(Optional.of(article));
        when(userService.findById(eq(review.getRenterId()))).thenReturn(Optional.empty());
        when(reviewDao.create(
                eq(review.getRating()),
                eq(review.getMessage()),
                eq(review.getArticleId()),
                eq(review.getRenterId())
        )).thenReturn(review);

        // Act
        reviewService.create(review.getRating(),review.getMessage(),review.getArticleId(), review.getRenterId());

        // Assert
        Assert.fail();
    }

    /* TODO: cambiar este test
    @Test
    public void testCreateFailArticleNotFound() {
        // Arrange
        when(articleService.findById(eq(review.getArticleId()))).thenReturn(Optional.empty());

        // Act
        Optional<Review> optionalReview = reviewService.create(review.getRating(),review.getMessage(),review.getArticleId(), review.getRenterId());

        // Assert
        Assert.assertFalse(optionalReview.isPresent());
    }

     */
}
