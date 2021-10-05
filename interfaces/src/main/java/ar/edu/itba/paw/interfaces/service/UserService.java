package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    List<User> list();

    Optional<User> register(String email, String password, String firstName, String lastName, Long location, MultipartFile img, UserType type);

    void update(long id, String firstName, String lastName, Long location, Boolean isOwner);

    void delete(long id);

    void updatePassword(long id, String password);
}
