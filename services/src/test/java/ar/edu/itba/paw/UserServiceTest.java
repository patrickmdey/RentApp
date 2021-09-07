package ar.edu.itba.paw;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserType;
import ar.edu.itba.paw.services.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    private static final String EMAIL = "email";

    private static final String PASSWORD = "password";

    private static final String LOCATION = "location";

    private static final String FIRST_NAME = "first name";

    private static final String LAST_NAME = "last name";

    private static final UserType TYPE = UserType.Owner;

    @InjectMocks
    private UserServiceImpl userService = new UserServiceImpl();

    @Mock
    private UserDao mockDao;

    @Test
    public void testRegister() {
        Mockito.when(mockDao.register(Mockito.eq(EMAIL), Mockito.eq(PASSWORD),
                        Mockito.eq(FIRST_NAME), Mockito.eq(LAST_NAME), Mockito.eq(LOCATION), Mockito.eq(TYPE.ordinal())))
                .thenReturn(Optional.of(new User(1, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, LOCATION, TYPE.ordinal())));

        Optional<User> optTestUser = userService.register(EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, LOCATION, TYPE.ordinal());

        Assert.assertTrue(optTestUser.isPresent());

        User testUser = optTestUser.get();

        Assert.assertEquals(EMAIL, testUser.getEmail());
        Assert.assertEquals(PASSWORD, testUser.getPassword());
        Assert.assertEquals(FIRST_NAME, testUser.getFirstName());
        Assert.assertEquals(LAST_NAME, testUser.getLastName());
        Assert.assertEquals(LOCATION, testUser.getLocation());
        Assert.assertEquals(TYPE, testUser.getType());
    }

    @Test
    public void testFindById() {
        Mockito.when(mockDao.register(Mockito.eq(EMAIL), Mockito.eq(PASSWORD),
                        Mockito.eq(FIRST_NAME), Mockito.eq(LAST_NAME), Mockito.eq(LOCATION), Mockito.eq(TYPE.ordinal())))
                .thenReturn(Optional.of(new User(1, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, LOCATION, TYPE.ordinal())));

        Optional<User> testUser = userService.findById((long) 1);

        Assert.assertNotNull(testUser);
        Assert.assertTrue(testUser.isPresent());

        Assert.assertEquals(EMAIL, testUser.get().getEmail());
        Assert.assertEquals(PASSWORD, testUser.get().getPassword());
        Assert.assertEquals(FIRST_NAME, testUser.get().getFirstName());
        Assert.assertEquals(LAST_NAME, testUser.get().getLastName());
        Assert.assertEquals(LOCATION, testUser.get().getLocation());
        Assert.assertEquals(TYPE, testUser.get().getType());
    }
}
