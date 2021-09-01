package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findById(Integer id);

    List<User> list();

    User register(String email, String password, String firstName, String lastName, String location, int type);
}
