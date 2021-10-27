package ar.edu.itba.paw;

import ar.edu.itba.paw.interfaces.dao.RentDao;
import ar.edu.itba.paw.interfaces.service.EmailService;
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

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RentServiceImplTest {

    @InjectMocks
    private RentServiceImpl rentService = new RentServiceImpl();

    @Mock
    private RentDao rentDao;
    @Mock
    private EmailService emailService;


    @Before
    public void setUp() throws ParseException {
        this.userOwner = new User(1, "owner@mail.com", "password",
                "owner", "owner", Locations.values()[3], null, UserType.OWNER);
        this.userRenter = new User(2, "renter@mail.com", "password", "renter",
                "renter", Locations.values()[5], null, UserType.RENTER);

        Article article = new Article(123, "bike", "fast bike", 400F, userOwner);

        this.rentProposal = new RentProposal(
                565,
                "I want to rent your bike",
                RentState.PENDING.ordinal(),
                new SimpleDateFormat("yyyy-MM-dd").parse("2021-11-15"),
                new SimpleDateFormat("yyyy-MM-dd").parse("2021-12-15")
        );

        this.rentProposal.setArticle(article);
        this.rentProposal.setRenter(userRenter);
    }

    private User userOwner;
    private User userRenter;
    private RentProposal rentProposal;


    @Test
    public void createSucceed() {
        // Arrange
        when(rentDao.create(
                eq(rentProposal.getMessage()),
                eq(rentProposal.getState()),
                eq(rentProposal.getStartDate()),
                eq(rentProposal.getEndDate()),
                eq(rentProposal.getArticle().getId()),
                eq(rentProposal.getRenter().getId())
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
                rentProposal.getArticle().getId(),
                userRenter.getFirstName(),
                userRenter.getEmail(),
                rentProposal.getRenter().getId()
        );

        // Assert
        Assert.assertEquals(rentProposal.getMessage(), result.getMessage());
        Assert.assertEquals(rentProposal.getStartDate(), result.getStartDate());
        Assert.assertEquals(rentProposal.getEndDate(), result.getEndDate());
        Assert.assertEquals(RentState.PENDING.ordinal(), (long) result.getState());

    }


    @Test(expected = ArticleNotFoundException.class)
    public void createFailArticleNotFound() {
        // Arrange
        when(rentDao.create(
                eq(rentProposal.getMessage()),
                eq(rentProposal.getState()),
                eq(rentProposal.getStartDate()),
                eq(rentProposal.getEndDate()),
                eq(rentProposal.getArticle().getId()),
                eq(rentProposal.getRenter().getId())
        )).thenThrow(ArticleNotFoundException.class);

        // Act
        rentService.create(
                rentProposal.getMessage(),
                rentProposal.getState(),
                rentProposal.getStartDate(),
                rentProposal.getEndDate(),
                rentProposal.getArticle().getId(),
                userRenter.getFirstName(),
                userRenter.getEmail(),
                rentProposal.getRenter().getId()
        );

        // Assert
        Assert.fail();

    }


    @Test(expected = UserNotFoundException.class)
    public void createFailOwnerNotFound() {
        // Arrange
        when(rentDao.create(
                eq(rentProposal.getMessage()),
                eq(rentProposal.getState()),
                eq(rentProposal.getStartDate()),
                eq(rentProposal.getEndDate()),
                eq(rentProposal.getArticle().getId()),
                eq(rentProposal.getRenter().getId())
        )).thenThrow(UserNotFoundException.class);

        // Act
        rentService.create(
                rentProposal.getMessage(),
                rentProposal.getState(),
                rentProposal.getStartDate(),
                rentProposal.getEndDate(),
                rentProposal.getArticle().getId(),
                userRenter.getFirstName(),
                userRenter.getEmail(),
                rentProposal.getRenter().getId()
        );

        // Assert
        Assert.fail();
    }


    @Test(expected = RuntimeException.class)
    public void createFailRentDaoThrowsException() {
        // Arrange
        when(rentDao.create(
                eq(rentProposal.getMessage()),
                eq(rentProposal.getState()),
                eq(rentProposal.getStartDate()),
                eq(rentProposal.getEndDate()),
                eq(rentProposal.getArticle().getId()),
                eq(rentProposal.getRenter().getId())
        )).thenThrow(RuntimeException.class);

        // Act
        rentService.create(
                rentProposal.getMessage(),
                rentProposal.getState(),
                rentProposal.getStartDate(),
                rentProposal.getEndDate(),
                rentProposal.getArticle().getId(),
                userRenter.getFirstName(),
                userRenter.getEmail(),
                rentProposal.getRenter().getId()
        );

        // Assert
        Assert.fail();

    }
}
