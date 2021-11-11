package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.models.DBImage;
import ar.edu.itba.paw.models.Locations;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserType;
import java.util.Optional;

public interface UserDao {

    Optional<User> findById(long id);

    User register(String email, String password, String firstName, String lastName, Locations location, DBImage img, UserType type);

    Optional<User> findByEmail(String email);

    void delete(long id);

}
