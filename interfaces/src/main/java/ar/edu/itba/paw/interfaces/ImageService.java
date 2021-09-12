package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.DBImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ImageService {

    Optional<DBImage> findById(long id);

    Optional<DBImage> create(MultipartFile img);

}
