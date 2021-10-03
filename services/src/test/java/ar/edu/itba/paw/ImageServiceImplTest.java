package ar.edu.itba.paw;

import ar.edu.itba.paw.interfaces.ImageDao;
import ar.edu.itba.paw.models.DBImage;
import ar.edu.itba.paw.services.ImageServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ImageServiceImplTest {

    @InjectMocks
    private ImageServiceImpl imageService = new ImageServiceImpl();

    @Mock
    private ImageDao imageDao;

    @Before
    public void setUp() throws IOException {

        image = new MockMultipartFile("/image/test.png","image/png");
        emptyImage = new MockMultipartFile("/invalidFile.png","image/png");
    }

    private MultipartFile image;
    private MultipartFile emptyImage;

    @Test
    public void testCreate() throws IOException {
        // Arrange
        DBImage dbImage = new DBImage(1,image.getBytes());
        when(imageDao.create(eq(image.getBytes())))
                .thenReturn(Optional.of(dbImage));

        // Act

        Optional<DBImage> optResult = imageService.create(image);

        // Assert
        Assert.assertTrue(optResult.isPresent());
        DBImage result = optResult.get();

        Assert.assertArrayEquals(image.getBytes(),result.getImg());

    }

    @Test
    public void testCreateFail() {
        // Arrange
        MultipartFile image = emptyImage;
        // Act

        Optional<DBImage> optResult = imageService.create(image);

        // Assert
        Assert.assertFalse(optResult.isPresent());

    }

}
