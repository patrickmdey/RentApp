package ar.edu.itba.paw;

import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.models.DBImage;
import ar.edu.itba.paw.models.Locations;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserType;
import ar.edu.itba.paw.models.exceptions.CannotCreateUserException;
import ar.edu.itba.paw.models.exceptions.CannotEditUserException;
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
    public void findByIdSucceed() {
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
    public void findByIdFailUserNotFound() {
        // Arrange
        final long userId = 999;

        // Act
        Optional<User> optionalUser = userDao.findById(userId);

        // Assert
        Assert.assertFalse(optionalUser.isPresent());
    }

    @Test
    public void registerSucceed() {
        // Arrange
        final String email  = "mail@mail.com";
        final String password = "pass";
        final String firstName = "first";
        final String lastName = "last";
        final Locations locationId = Locations.CHACARITA;
        final DBImage image = null;
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
    public void registerFailNullValuesBeforeInsert() {
        // Arrange
        final String email  = null;
        final String password = null;
        final String firstName = null;
        final String lastName = null;
        final Locations locationId = null;
        final DBImage image = null;
        final UserType type = null;

        // Act
        userDao.register(email,password,firstName,lastName,locationId,image,type);

        // Assert
        Assert.fail();
    }

    @Test(expected = CannotCreateUserException.class)
    public void registerFailNullValuesDuringInsert() {
        // Arrange
        final String email  = null;
        final String password = null;
        final String firstName = null;
        final String lastName = null;
        final Locations locationId = Locations.CHACARITA;
        final DBImage image = null;
        final UserType type = UserType.OWNER;

        // Act
        userDao.register(email,password,firstName,lastName,locationId,image,type);

        // Assert
        Assert.fail();
    }

    @Test(expected = Test.None.class)
    public void updateSucceed() {
        // Arrange
        final long userId = 1;
        final String firstName = "first";
        final String lastName = "last";
        final Locations location = Locations.CHACARITA;

        // Act
        User user = userDao.findById(userId).orElse(null);
        if (user == null)
            Assert.fail();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setLocation(location);

        // Assert
        Assert.assertEquals(user.getFirstName(), firstName);
        Assert.assertEquals(user.getLastName(), lastName);
        Assert.assertEquals(user.getLocation(), location);

    }

    @Test(expected = CannotEditUserException.class)
    public void updateFailNullValuesBeforeUpdate() {
        // Arrange
        final long userId = 1;
        final String firstName = null;
        final String lastName = null;
        final Locations location = null;

        // Act
        User user = userDao.findById(userId).orElse(null);
        if (user == null)
            Assert.fail();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setLocation(location);

        // Assert
        Assert.fail();
    }

    @Test(expected = CannotEditUserException.class)
    public void updateFailNullValuesDuringUpdate() {
        // Arrange
        final long userId = 1;
        final String firstName = null;
        final String lastName = null;
        final Locations location = Locations.AGRONOMIA;

        User user = userDao.findById(userId).orElse(null);
        if (user == null)
            Assert.fail();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setLocation(location);

        // Assert
        Assert.fail();
    }

    @Test(expected = Test.None.class)
    public void deleteSucceed() {
        // Arrange
        final long userId = 1;

        // Act
        userDao.delete(userId);

        // Assert
    }

    @Test(expected = Test.None.class)
    public void updatePasswordSucceed() {
        // Arrange
        final long userId = 1;
        final String passwordHash = "new hash";

        // Act
        User user = userDao.findById(userId).orElse(null);
        if (user == null)
            Assert.fail();

        user.setPassword(passwordHash);

        // Assert
    }

    @Test(expected = CannotEditUserException.class)
    public void updatePasswordFailNullValues() {
        // Arrange
        final long userId = 1;
        final String passwordHash = null;

        User user = userDao.findById(userId).orElse(null);
        if (user == null)
            Assert.fail();

        user.setPassword(passwordHash);

        // Assert
        Assert.fail();
    }



}















