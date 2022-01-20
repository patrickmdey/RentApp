package ar.edu.itba.paw.helpers;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class MockMultipartFile/* implements MultipartFile*/ {

    private final ClassPathResource resource;
    private final String contentType;

    public MockMultipartFile(String name, String contentType) {
        resource = new ClassPathResource(name);
        this.contentType = contentType;
    }

    //@Override
    public String getName() {
        return resource.getFilename();
    }

    //@Override
    public String getOriginalFilename() {
        return resource.getFilename();
    }

    //@Override
    public String getContentType() {
        return contentType;
    }

    //@Override
    public boolean isEmpty() {
        return getSize() == 0;
    }

    //@Override
    public long getSize() {
        try {
            return Files.readAllBytes(resource.getFile().toPath()).length;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    //@Override
    public byte[] getBytes() throws IOException {
        return Files.readAllBytes(resource.getFile().toPath());
    }

    //@Override
    public InputStream getInputStream() throws IOException {
        return this.resource.getInputStream();
    }

    //@Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        FileCopyUtils.copy(this.getBytes(),dest);
    }
}
