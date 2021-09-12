package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserType;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findById(long id);

    Optional<User> findByEmail(String email);

    List<User> list();

    Optional<User> register(String email, String password, String confirmPassword, String firstName, String lastName, String location, UserType type);
}
