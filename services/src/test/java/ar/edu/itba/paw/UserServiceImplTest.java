package ar.edu.itba.paw;

import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.interfaces.service.EmailService;
import ar.edu.itba.paw.interfaces.service.ImageService;
import ar.edu.itba.paw.models.DBImage;
import ar.edu.itba.paw.models.Locations;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserType;
import ar.edu.itba.paw.services.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.PersistenceException;
import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService = new UserServiceImpl();

    @Mock
    private UserDao userDao;
    @Mock
    private ImageService imageService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private EmailService emailService;

    @Before
    public void setUp() {
        this.user = new User(
                123,
                "user@mail.com",
                "password hash",
                "name",
                "last name",
                Locations.CHACARITA,
                null,
                UserType.RENTER
        );
        image = new MockMultipartFile("/image/test.png", "image/png");
    }

    private User user;
    private MultipartFile image;


    @Test
    public void registerSucceed() throws IOException {
        // Arrange
        final String password = "password";
        final DBImage uploadedImage = new DBImage(432, image.getBytes());

        when(passwordEncoder.encode(eq(password))).thenReturn(user.getPassword());
        when(imageService.create(eq(image))).thenReturn(uploadedImage);
        when(userDao.register(eq(user.getEmail()),
                eq(user.getPassword()),
                eq(user.getFirstName()),
                eq(user.getLastName()),
                eq(user.getLocation()),
                eq(uploadedImage),
                eq(user.getType())
        )).thenReturn(user);
        doNothing().when(emailService).sendNewUserMail(eq(user));

        // Act
        User result = userService.register(user.getEmail(), password,
                user.getFirstName(), user.getLastName(),
                (long) user.getLocation().ordinal(), image, user.getType()
        );

        // Assert
        Assert.assertEquals(user.getEmail(), result.getEmail());
        Assert.assertEquals(user.getFirstName(), result.getFirstName());
        Assert.assertEquals(user.getLastName(), result.getLastName());
        Assert.assertEquals(user.getType(), result.getType());

    }

    @Test(expected = PersistenceException.class)
    public void registerFailUserDaoThrowsException() throws IOException {
        // Arrange
        final String password = "password";
        final DBImage uploadedImage = new DBImage(432, image.getBytes());

        when(passwordEncoder.encode(eq(password))).thenReturn(user.getPassword());
        when(imageService.create(eq(image))).thenReturn(uploadedImage);
        when(userDao.register(eq(user.getEmail()), eq(user.getPassword()),
                eq(user.getFirstName()), eq(user.getLastName()),
                eq(user.getLocation()), eq(uploadedImage),
                eq(user.getType())
        )).thenThrow(PersistenceException.class);

        // Act
        userService.register(
                user.getEmail(), password, user.getFirstName(), user.getLastName(),
                (long) user.getLocation().ordinal(), image, user.getType()
        );

        // Assert
        Assert.fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void registerFailImageNotUploaded() throws IOException {
        // Arrange
        final String password = "password";

        when(imageService.create(eq(image))).thenThrow(IllegalArgumentException.class);

        // Act
        userService.register(
                user.getEmail(), password, user.getFirstName(), user.getLastName(),
                (long) user.getLocation().ordinal(), image, user.getType()
        );

        // Assert
        Assert.fail();

    }

}
