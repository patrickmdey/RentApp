package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.models.Locations;
import ar.edu.itba.paw.models.User;
import java.util.Optional;

public interface UserService {
    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    User register(String email, String password, String firstName, String lastName, Locations location, byte[] img, boolean isOwner, String webpageUrl);

    void update(long id, String firstName, String lastName, Locations location);

    void delete(long id);

    void updatePassword(long id, String password);
}
