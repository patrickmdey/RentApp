package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    User get(Integer id);

    List<User> list();

    Optional<User> findById(long id);

    User save(User user);

    User register(String email, String password, String firstName, String lastName, String location, int type);
}
