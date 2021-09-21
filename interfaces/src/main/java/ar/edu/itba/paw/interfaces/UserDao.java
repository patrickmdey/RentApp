package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> get(long id);

    List<User> list();

    Optional<User> findById(long id);

    User save(User user);

    Optional<User> register(String email, String password, String firstName, String lastName, Long location, Long img, int type);

    Optional<User> findByEmail(String email);

    void update(long id, String firstName, String lastName, String email, Long location, int type);

    void delete(long id);
}
