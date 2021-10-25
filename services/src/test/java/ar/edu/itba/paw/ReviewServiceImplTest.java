package ar.edu.itba.paw;

import ar.edu.itba.paw.interfaces.dao.ReviewDao;
import ar.edu.itba.paw.interfaces.service.ArticleService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.ArticleNotFoundException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
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

        this.article = new Article(123,"bike", "fast bike", 400F,userOwner);

        this.review = new Review(
                786,
                "Good product",
                new Date(System.currentTimeMillis())
        );
        this.review.setArticle(this.article);
        this.review.setRenter(this.userRenter);
    }

    private User userOwner;
    private User userRenter;
    private Article article;
    private Review review;

    @Test
    public void createSucceed() {
        // Arrange
        when(articleService.findById(eq(review.getArticle().getId()))).thenReturn(Optional.of(article));
        when(userService.findById(eq(review.getRenter().getId()))).thenReturn(Optional.of(userRenter));
        when(reviewDao.create(
                eq(review.getRating()),
                eq(review.getMessage()),
                eq(review.getArticle().getId()),
                eq(review.getRenter().getId())
        )).thenReturn(review);

        // Act
        Review result = reviewService.create(review.getRating(),review.getMessage(),review.getArticle().getId(), review.getRenter().getId());

        // Assert
        Assert.assertEquals(review.getMessage(), result.getMessage());
        Assert.assertEquals(review.getRating(), result.getRating());
        Assert.assertEquals(review.getArticle().getId(), result.getArticle().getId());
        Assert.assertEquals(review.getRenter().getId(), result.getRenter().getId());
    }

    @Test(expected = RuntimeException.class)
    public void createFailReviewDaoThrowsException() {
        // Arrange
        when(articleService.findById(eq(review.getArticle().getId()))).thenReturn(Optional.of(article));
        when(reviewDao.create(
                eq(review.getRating()),
                eq(review.getMessage()),
                eq(review.getArticle().getId()),
                eq(review.getRenter().getId())
        )).thenThrow(RuntimeException.class);

        // Act
        reviewService.create(review.getRating(), review.getMessage(), review.getArticle().getId(), review.getRenter().getId());

        // Assert
        Assert.fail();
    }

    @Test(expected = UserNotFoundException.class)
    public void createFailUserNotFound() {
        // Arrange
        when(articleService.findById(eq(review.getArticle().getId()))).thenReturn(Optional.of(article));
        when(userService.findById(eq(review.getRenter().getId()))).thenReturn(Optional.empty());
        when(reviewDao.create(
                eq(review.getRating()),
                eq(review.getMessage()),
                eq(review.getArticle().getId()),
                eq(review.getRenter().getId())
        )).thenReturn(review);

        // Act
        reviewService.create(review.getRating(), review.getMessage(), review.getArticle().getId(), review.getRenter().getId());

        // Assert
        Assert.fail();
    }

    @Test(expected = ArticleNotFoundException.class)
    public void createFailArticleNotFound() {
        // Arrange
        when(articleService.findById(eq(review.getArticle().getId()))).thenReturn(Optional.empty());

        // Act
        reviewService.create(review.getRating(), review.getMessage(), review.getArticle().getId(), review.getRenter().getId());

        // Assert
        Assert.fail();
    }

}
