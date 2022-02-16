package ar.edu.itba.paw.helpers;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class MockMultipartFile {

    private final ClassPathResource resource;
    private final String contentType;

    public MockMultipartFile(String name, String contentType) {
        resource = new ClassPathResource(name);
        this.contentType = contentType;
    }

    public String getName() {
        return resource.getFilename();
    }

    public String getOriginalFilename() {
        return resource.getFilename();
    }

    public String getContentType() {
        return contentType;
    }

    public boolean isEmpty() {
        return getSize() == 0;
    }

    public long getSize() {
        try {
            return Files.readAllBytes(resource.getFile().toPath()).length;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public byte[] getBytes() throws IOException {
        return Files.readAllBytes(resource.getFile().toPath());
    }

    public InputStream getInputStream() throws IOException {
        return this.resource.getInputStream();
    }

    public void transferTo(File dest) throws IOException, IllegalStateException {
        FileCopyUtils.copy(this.getBytes(),dest);
    }
}
