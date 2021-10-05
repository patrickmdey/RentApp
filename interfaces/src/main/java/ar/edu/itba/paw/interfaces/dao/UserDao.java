package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.models.Locations;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserType;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    List<User> list();

    Optional<User> findById(long id);

    Optional<User> register(String email, String password, String firstName, String lastName, Locations location, Long img, UserType type);

    Optional<User> findByEmail(String email);

    void update(long id, String firstName, String lastName, Locations location, UserType type);

    void delete(long id);

    void updatePassword(long id, String passwordHash);
}
