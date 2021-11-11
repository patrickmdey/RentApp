package ar.edu.itba.paw;

import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.models.DBImage;
import ar.edu.itba.paw.models.Locations;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserType;
import ar.edu.itba.paw.models.exceptions.CannotCreateUserException;
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

@Sql("classpath:populateUserTest.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Rollback()
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @PersistenceContext
    private EntityManager em;

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
        final String email = "mail@mail.com";
        final String password = "pass";
        final String firstName = "first";
        final String lastName = "last";
        final Locations locationId = Locations.CHACARITA;
        final UserType type = UserType.OWNER;
        final DBImage image = em.find(DBImage.class, 3L);

        // Act
        User u = userDao.register(email, password, firstName, lastName, locationId, image, type);

        // Assert
        User user = em.find(User.class, u.getId());

        Assert.assertEquals(email, user.getEmail());
        Assert.assertEquals(password, user.getPassword());
        Assert.assertEquals(firstName, user.getFirstName());
        Assert.assertEquals(lastName, user.getLastName());
        Assert.assertEquals(locationId, user.getLocation());
        Assert.assertEquals(type, user.getType());
    }

    @Test(expected = CannotCreateUserException.class)
    public void registerFailNullValues() {
        // Arrange
        final String email = null;
        final String password = null;
        final String firstName = null;
        final String lastName = null;
        final Locations locationId = null;
        final DBImage image = null;
        final UserType type = null;

        // Act
        userDao.register(email, password, firstName, lastName, locationId, image, type);

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
        Assert.assertNull(em.find(User.class, userId));
    }

    @Test(expected = UserNotFoundException.class)
    public void deleteFailUserNotFound() {
        // Arrange
        final long userId = 999;

        // Act
        userDao.delete(userId);

        // Assert
        Assert.fail();
    }

}















