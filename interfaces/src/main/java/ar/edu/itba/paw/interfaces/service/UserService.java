package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserType;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;

public interface UserService {
    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    User register(String email, String password, String firstName, String lastName, long location, MultipartFile img, UserType type, String webpageUrl);

    void update(long id, String firstName, String lastName, long location);

    void delete(long id);

    void updatePassword(long id, String password);
}
