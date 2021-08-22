package ar.edu.itba.paw;

import java.util.List;

public interface UserService {
    User findById(String id);
    List<User> list();
}
