package ar.edu.itba.paw;

import ar.edu.itba.paw.interfaces.dao.RentDao;
import ar.edu.itba.paw.interfaces.service.ArticleService;
import ar.edu.itba.paw.interfaces.service.EmailService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.ArticleNotFoundException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import ar.edu.itba.paw.services.RentServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RentServiceImplTest {

    @InjectMocks
    private RentServiceImpl rentService = new RentServiceImpl();

    @Mock
    private RentDao rentDao;
    @Mock
    private ArticleService articleService;
    @Mock
    private UserService userService;
    @Mock
    private EmailService emailService;


    @Before
    public void setUp() throws ParseException {
        this.userOwner = new User(1, "owner@mail.com", "password",
                "owner", "owner", Locations.values()[3], null, UserType.OWNER);
        this.userRenter = new User(2, "renter@mail.com", "password", "renter",
                "renter", Locations.values()[5], null, UserType.RENTER);

        this.article = new Article(123, "bike", "fast bike", 400F, userOwner.getId());

        this.rentProposal = new RentProposal(
                565,
                "I want to rent your bike",
                RentState.PENDING.ordinal(),
                new SimpleDateFormat("yyyy-MM-dd").parse("2021-11-15"),
                new SimpleDateFormat("yyyy-MM-dd").parse("2021-12-15"),
                this.article.getId(),
                this.userRenter.getId()
        );
    }

    private User userOwner;
    private User userRenter;
    private Article article;
    private RentProposal rentProposal;


    @Test
    public void create_Succeed() {
        // Arrange
        when(articleService.findById(eq(article.getId()))).thenReturn(Optional.of(article));

        when(userService.findById(eq(userRenter.getId()))).thenReturn(Optional.of(userRenter));
        when(userService.findById(eq(userOwner.getId()))).thenReturn(Optional.of(userOwner));

        when(rentDao.create(
                eq(rentProposal.getMessage()),
                eq(rentProposal.getState()),
                eq(rentProposal.getStartDate()),
                eq(rentProposal.getEndDate()),
                eq(rentProposal.getArticleId()),
                eq(rentProposal.getRenterId())
        )).thenReturn(rentProposal);

        doNothing().when(emailService).sendMailRequest(
                eq(rentProposal), eq(userOwner)
        );

        // Act
        RentProposal result = rentService.create(
                rentProposal.getMessage(),
                rentProposal.getState(),
                rentProposal.getStartDate(),
                rentProposal.getEndDate(),
                rentProposal.getArticleId(),
                userRenter.getFirstName(),
                userRenter.getEmail(),
                rentProposal.getRenterId()
        );

        // Assert
        Assert.assertEquals(rentProposal.getMessage(), result.getMessage());
        Assert.assertEquals(rentProposal.getStartDate(), result.getStartDate());
        Assert.assertEquals(rentProposal.getEndDate(), result.getEndDate());
        Assert.assertEquals(RentState.PENDING.ordinal(), (long) result.getState());

    }


    @Test(expected = ArticleNotFoundException.class)
    public void create_Fail_ArticleNotFound() {
        // Arrange
        when(articleService.findById(eq(rentProposal.getArticleId()))).thenReturn(Optional.empty());

        // Act
        rentService.create(
                rentProposal.getMessage(),
                rentProposal.getState(),
                rentProposal.getStartDate(),
                rentProposal.getEndDate(),
                rentProposal.getArticleId(),
                userRenter.getFirstName(),
                userRenter.getEmail(),
                rentProposal.getRenterId()
        );

        // Assert
        Assert.fail();

    }


    @Test(expected = UserNotFoundException.class)
    public void create_Fail_OwnerNotFound() {
        // Arrange
        when(articleService.findById(eq(rentProposal.getArticleId()))).thenReturn(Optional.of(article));
        when(userService.findById(eq(userOwner.getId()))).thenReturn(Optional.empty());

        // Act
        rentService.create(
                rentProposal.getMessage(),
                rentProposal.getState(),
                rentProposal.getStartDate(),
                rentProposal.getEndDate(),
                rentProposal.getArticleId(),
                userRenter.getFirstName(),
                userRenter.getEmail(),
                rentProposal.getRenterId()
        );

        // Assert
        Assert.fail();
    }


    @Test(expected = RuntimeException.class)
    public void create_Fail_RentDaoThrowsException() {
        // Arrange
        when(articleService.findById(eq(rentProposal.getArticleId()))).thenReturn(Optional.of(article));
        when(userService.findById(eq(userOwner.getId()))).thenReturn(Optional.of(userOwner));

        when(rentDao.create(
                eq(rentProposal.getMessage()),
                eq(rentProposal.getState()),
                eq(rentProposal.getStartDate()),
                eq(rentProposal.getEndDate()),
                eq(rentProposal.getArticleId()),
                eq(rentProposal.getRenterId())
        )).thenThrow(RuntimeException.class);

        // Act
        rentService.create(
                rentProposal.getMessage(),
                rentProposal.getState(),
                rentProposal.getStartDate(),
                rentProposal.getEndDate(),
                rentProposal.getArticleId(),
                userRenter.getFirstName(),
                userRenter.getEmail(),
                rentProposal.getRenterId()
        );

        // Assert
        Assert.fail();

    }
}
