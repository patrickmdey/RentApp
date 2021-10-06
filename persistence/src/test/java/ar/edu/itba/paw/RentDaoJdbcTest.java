package ar.edu.itba.paw;

import ar.edu.itba.paw.interfaces.dao.RentDao;
import ar.edu.itba.paw.models.RentProposal;
import ar.edu.itba.paw.models.RentState;
import ar.edu.itba.paw.models.exceptions.CannotCreateProposalException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Sql("classpath:populateRentTest.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class RentDaoJdbcTest {
    @Autowired
    private RentDao rentDao;
    @Autowired
    private DataSource dataSource;

    @After
    public void cleanUp(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        JdbcTestUtils.deleteFromTables(jdbcTemplate,
                "article",
                "category",
                "article_category",
                "account"
        );
    }

    @Test
    public void ownerRequests_Succeed() {
        // Arrange
        final long idOwner = 1;
        final int state = RentState.ACCEPTED.ordinal();
        final long page = 1;
        final long[] expectedIds  = {2,3};

        // Act
        List<RentProposal> result = rentDao.ownerRequests(idOwner,state,page);

        // Assert
        Assert.assertArrayEquals(expectedIds,result.stream().mapToLong(RentProposal::getId).toArray());
    }

    @Test
    public void sentRequests_Succeed() {
        // Arrange
        final long idOwner = 1;
        final int state = RentState.PENDING.ordinal();
        final long page = 1;
        final long[] expectedIds  = {1};

        // Act
        List<RentProposal> result = rentDao.ownerRequests(idOwner,state,page);

        // Assert
        Assert.assertArrayEquals(expectedIds,result.stream().mapToLong(RentProposal::getId).toArray());
    }

    @Test
    public void findById_Succeed() {
        // Arrange
        final long idRentProposal = 1;
        final String expectedMessage = "can I rent 1";

        // Act
        Optional<RentProposal> optionalResult = rentDao.findById(idRentProposal);

        // Assert
        Assert.assertTrue(optionalResult.isPresent());
        RentProposal result = optionalResult.get();

        Assert.assertEquals(idRentProposal, result.getId());
        Assert.assertEquals(expectedMessage, result.getMessage());
    }

    @Test
    public void findById_Fail_RentProposalNotFound() {
        // Arrange
        final long idRentProposal = 112321;

        // Act
        Optional<RentProposal> optionalResult = rentDao.findById(idRentProposal);

        // Assert
        Assert.assertFalse(optionalResult.isPresent());
    }

    @Test
    public void create_Succeed() throws ParseException {
        // Assert
        final String comment = "";
        final Integer state = 1;
        final Date startDate = new SimpleDateFormat("yyyy-MM-dd").
                parse("2021-11-15");
        final Date endDate = new SimpleDateFormat("yyyy-MM-dd")
                .parse("2021-11-15");
        final long articleId = 1L;
        final long renterId = 1;

        // Act
        RentProposal result = rentDao.create(comment, state, startDate,endDate, articleId,renterId);

        // Assert

        Assert.assertEquals(comment,result.getMessage());
        Assert.assertEquals(state,result.getState());
        Assert.assertEquals(startDate,result.getStartDate());
        Assert.assertEquals(endDate,result.getEndDate());
        Assert.assertEquals(articleId,result.getArticleId());
        Assert.assertEquals(renterId,result.getRenterId());
    }

    @Test(expected = CannotCreateProposalException.class)
    public void create_Fail_ProposalExists() throws ParseException {
        // Assert
        final String comment = "";
        final Integer state = 1;
        final Date startDate = new SimpleDateFormat("yyyy-MM-dd").
                parse("2021-10-05");
        final Date endDate = new SimpleDateFormat("yyyy-MM-dd")
                .parse("2021-11-01");
        final long articleId = 1L;
        final long renterId = 2;

        // Act
        rentDao.create(comment, state, startDate,endDate, articleId,renterId);

        // Assert
        Assert.fail();
    }

    @Test(expected = CannotCreateProposalException.class)
    public void create_Fail_ArticleNotFound() throws ParseException {
        // Assert
        final String comment = "";
        final Integer state = 1;
        final Date startDate = new SimpleDateFormat("yyyy-MM-dd").
                parse("2021-10-05");
        final Date endDate = new SimpleDateFormat("yyyy-MM-dd")
                .parse("2021-11-01");
        final long articleId = 999;
        final long renterId = 1;

        // Act
        rentDao.create(comment, state, startDate,endDate, articleId,renterId);

        // Assert
        Assert.fail();
    }

    @Test(expected = CannotCreateProposalException.class)
    public void create_Fail_UserNotFound() throws ParseException {
        // Assert
        final String comment = "";
        final Integer state = 1;
        final Date startDate = new SimpleDateFormat("yyyy-MM-dd").
                parse("2021-10-05");
        final Date endDate = new SimpleDateFormat("yyyy-MM-dd")
                .parse("2021-11-01");
        final long articleId = 2;
        final long renterId = 9999;

        // Act
        rentDao.create(comment, state, startDate,endDate, articleId,renterId);

        // Assert
        Assert.fail();
    }

    @Test(expected = Test.None.class)
    public void updateRequest_Succeed() {
        // Arrange
        final long requestId = 1;
        final int state = RentState.ACCEPTED.ordinal();

        // Act
        rentDao.updateRequest(requestId,state);

        // Assert

    }

    @Test
    public void hasRented_Succeed_ReturnTrue() {
        // Arrange
        final long renterId = 2;
        final long articleId  = 2;

        // Act
        boolean result = rentDao.hasRented(renterId,articleId);

        // Assert
        Assert.assertTrue(result);
    }

    @Test
    public void hasRented_Succeed_ReturnFalse() {
        // Arrange
        final long renterId = 2;
        final long articleId  = 1;

        // Act
        boolean result = rentDao.hasRented(renterId,articleId);

        // Assert
        Assert.assertFalse(result);
    }

}





















