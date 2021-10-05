package ar.edu.itba.paw;

import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.interfaces.ImageService;
import ar.edu.itba.paw.interfaces.UserDao;
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

import java.io.IOException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
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
        emptyImage = new MockMultipartFile("/invalidFile.png", "image/png");

    }

    private User user;
    private MultipartFile image;
    private MultipartFile emptyImage;


    @Test
    public void testRegister() throws IOException {
        // Arrange
        final String password = "password";
        final DBImage uploadedImage = new DBImage(432, image.getBytes());

        when(passwordEncoder.encode(eq(password))).thenReturn(user.getPassword());
        when(imageService.create(eq(image))).thenReturn(Optional.of(uploadedImage));
        when(userDao.register(eq(user.getEmail()),
                eq(user.getPassword()),
                eq(user.getFirstName()),
                eq(user.getLastName()),
                eq(user.getLocation()),
                eq(uploadedImage.getId()),
                eq(user.getType())
        )).thenReturn(Optional.of(user));
        doNothing().when(emailService).sendNewUserMail(eq(user));

        // Act
        Optional<User> optionalResult = userService.register(user.getEmail(), password,
                user.getFirstName(), user.getLastName(),
                (long) user.getLocation().ordinal(), image, user.getType()
        );

        // Assert
        Assert.assertTrue(optionalResult.isPresent());
        User result = optionalResult.get();

        Assert.assertEquals(user.getEmail(), result.getEmail());
        Assert.assertEquals(user.getFirstName(), result.getFirstName());
        Assert.assertEquals(user.getLastName(), result.getLastName());
        Assert.assertEquals(user.getType(), result.getType());

    }

    @Test(expected = RuntimeException.class)
    public void testRegisterFails() throws IOException {
        // Arrange
        final String password = "password";
        final DBImage uploadedImage = new DBImage(432, image.getBytes());

        when(passwordEncoder.encode(eq(password))).thenReturn(user.getPassword());
        when(imageService.create(eq(image))).thenReturn(Optional.of(uploadedImage));
        when(userDao.register(eq(user.getEmail()), eq(user.getPassword()),
                eq(user.getFirstName()), eq(user.getLastName()),
                eq(user.getLocation()), eq(uploadedImage.getId()),
                eq(user.getType())
        )).thenThrow(RuntimeException.class);

        // Act
        userService.register(
                user.getEmail(), password, user.getFirstName(), user.getLastName(),
                (long) user.getLocation().ordinal(), image, user.getType()
        );

        // Assert
        Assert.fail();
    }

    @Test
    public void testRegisterFailImageNotUploaded() throws IOException {
        // Arrange
        final String password = "password";

        when(passwordEncoder.encode(eq(password))).thenReturn(user.getPassword());
        when(imageService.create(eq(image))).thenReturn(Optional.empty());

        // Act
        Optional<User> optionalResult = userService.register(
                user.getEmail(), password, user.getFirstName(), user.getLastName(),
                (long) user.getLocation().ordinal(), image, user.getType()
        );

        // Assert
        Assert.assertFalse(optionalResult.isPresent());

    }
}
