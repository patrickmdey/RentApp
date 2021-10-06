package ar.edu.itba.paw;

import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.models.Locations;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserType;
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
import java.util.List;
import java.util.Optional;

@Sql("classpath:populateUserTest.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class UserDaoJdbcTest {

    @Autowired
    private UserDao userDao;
    @Autowired
    private DataSource dataSource;

    @After
    public void cleanUp(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        JdbcTestUtils.deleteFromTables(jdbcTemplate,
                "account"
        );
    }

    @Test
    public void list_Succeed() {
        // Arrange
        final long[] expectedIds = {1,2};

        // Act
        List<User> users = userDao.list();

        // Assert
        Assert.assertArrayEquals(expectedIds,users.stream().mapToLong(User::getId).toArray());
    }

    @Test
    public void findById_Succeed() {
        // Arrange
        final long userId = 1;

        // Act
        Optional<User> optionalUser = userDao.findById(userId);

        // Assert
        Assert.assertTrue(optionalUser.isPresent());
        User user = optionalUser.get();

        Assert.assertEquals(userId, user.getId());
    }

    @Test
    public void findById_Fail_UserNotFound() {
        // Arrange
        final long userId = 999;

        // Act
        Optional<User> optionalUser = userDao.findById(userId);

        // Assert
        Assert.assertFalse(optionalUser.isPresent());
    }

    @Test
    public void register_Succeed() {
        // Arrange
        final String email  = "mail@mail.com";
        final String password = "pass";
        final String firstName = "first";
        final String lastName = "last";
        final Locations locationId = Locations.CHACARITA;
        final Long image = null;
        final UserType type = UserType.OWNER;

        // Act
        User user = userDao.register(email,password,firstName,lastName,locationId,image,type);

        // Assert
        Assert.assertEquals(email, user.getEmail());
        Assert.assertEquals(password, user.getPassword());
        Assert.assertEquals(firstName, user.getFirstName());
        Assert.assertEquals(lastName, user.getLastName());
        Assert.assertEquals(locationId, user.getLocation());
        Assert.assertEquals(type, user.getType());
    }

    @Test(expected = NullPointerException.class)
    public void register_Fail_NullValuesBeforeInsert() {
        // Arrange
        final String email  = null;
        final String password = null;
        final String firstName = null;
        final String lastName = null;
        final Locations locationId = null;
        final Long image = null;
        final UserType type = null;

        // Act
        userDao.register(email,password,firstName,lastName,locationId,image,type);

        // Assert
        Assert.fail();
    }

    @Test(expected = DataAccessException.class)
    public void register_Fail_NullValuesDuringInsert() {
        // Arrange
        final String email  = null;
        final String password = null;
        final String firstName = null;
        final String lastName = null;
        final Locations locationId = Locations.CHACARITA;
        final Long image = null;
        final UserType type = UserType.OWNER;

        // Act
        userDao.register(email,password,firstName,lastName,locationId,image,type);

        // Assert
        Assert.fail();
    }

    @Test(expected = Test.None.class)
    public void update_Succeed() {
        // Arrange
        final long userId = 1;
        final String firstName = "first";
        final String lastName = "last";
        final Locations location = Locations.CHACARITA;

        // Act
        userDao.update(userId,firstName,lastName,location);

        // Assert

    }

    @Test(expected = NullPointerException.class)
    public void update_Fail_NullValuesBeforeUpdate() {
        // Arrange
        final long userId = 1;
        final String firstName = null;
        final String lastName = null;
        final Locations location = null;

        // Act
        userDao.update(userId,firstName,lastName,location);

        // Assert
        Assert.fail();
    }

    @Test(expected = DataAccessException.class)
    public void update_Fail_NullValuesDuringUpdate() {
        // Arrange
        final long userId = 1;
        final String firstName = null;
        final String lastName = null;
        final Locations location = Locations.AGRONOMIA;

        // Act
        userDao.update(userId,firstName,lastName,location);

        // Assert
        Assert.fail();
    }

    @Test(expected = Test.None.class)
    public void delete_Succeed() {
        // Arrange
        final long userId = 1;

        // Act
        userDao.delete(userId);

        // Assert
    }

    @Test(expected = Test.None.class)
    public void updatePassword_Succeed() {
        // Arrange
        final long userId = 1;
        final String passwordHash = "new hash";

        // Act
        userDao.updatePassword(userId,passwordHash);

        // Assert
    }

    @Test(expected = DataAccessException.class)
    public void updatePassword_Fail_NullValues() {
        // Arrange
        final long userId = 1;
        final String passwordHash = null;

        // Act
        userDao.updatePassword(userId,passwordHash);

        // Assert
        Assert.fail();
    }



}















