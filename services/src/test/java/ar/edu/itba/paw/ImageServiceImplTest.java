package ar.edu.itba.paw;

import ar.edu.itba.paw.helpers.MockMultipartFile;
import ar.edu.itba.paw.interfaces.dao.ImageDao;
import ar.edu.itba.paw.models.DBImage;
import ar.edu.itba.paw.models.exceptions.CannotCreateImageException;
import ar.edu.itba.paw.services.ImageServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.io.IOException;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ImageServiceImplTest {

    @InjectMocks
    private ImageServiceImpl imageService = new ImageServiceImpl();

    @Mock
    private ImageDao imageDao;

    @Before
    public void setUp() {
        image = new MockMultipartFile("/image/test.png", "image/png");
    }

    private MockMultipartFile image;

    @Test
    public void createSucceed() throws IOException {
        // Arrange
        DBImage dbImage = new DBImage(1, image.getBytes());
        when(imageDao.create(eq(image.getBytes())))
                .thenReturn(dbImage);

        // Act
        DBImage result = imageService.create(image.getBytes());


        // Assert
        Assert.assertArrayEquals(image.getBytes(), result.getImg());
    }

    @Test(expected = CannotCreateImageException.class)
    public void createFailImageIsEmpty() {
        // Arrange
        byte[] empty = new byte[0];
        when(imageDao.create(eq(empty))).thenThrow(CannotCreateImageException.class);

        // Act
        imageService.create(empty);

        // Assert
        Assert.fail();

    }

}
