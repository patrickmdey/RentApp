package ar.edu.itba.paw;

import java.util.List;

public interface UserDao {
    User get(String id);
    List<User> list();
    User save(User user);
}
