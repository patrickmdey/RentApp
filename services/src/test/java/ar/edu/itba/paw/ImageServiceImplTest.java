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
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
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

    private class MockMultipartFile implements MultipartFile {

        private final ClassPathResource resource;
        private final String contentType;

        public MockMultipartFile(String name, String contentType) {
            resource = new ClassPathResource(name);
            this.contentType = contentType;
        }

        @Override
        public String getName() {
            return resource.getFilename();
        }

        @Override
        public String getOriginalFilename() {
            return resource.getFilename();
        }

        @Override
        public String getContentType() {
            return contentType;
        }

        @Override
        public boolean isEmpty() {
            return getSize() == 0;
        }

        @Override
        public long getSize() {
            try {
                return Files.readAllBytes(resource.getFile().toPath()).length;
            } catch (IOException e) {
                e.printStackTrace();
                return 0;
            }
        }

        @Override
        public byte[] getBytes() throws IOException {
            return Files.readAllBytes(resource.getFile().toPath());
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return this.resource.getInputStream();
        }

        @Override
        public void transferTo(File dest) throws IOException, IllegalStateException {
            FileCopyUtils.copy(this.getBytes(),dest);
        }
    }
}
