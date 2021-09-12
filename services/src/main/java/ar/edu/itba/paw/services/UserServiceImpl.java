package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> findById(long id) {
        return this.userDao.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.userDao.findByEmail(email);
    }

    @Override
    public List<User> list() {
        return this.userDao.list();
    }

    @Override
    public Optional<User> register(String email, String password, String confirmPassword, String firstName, String lastName, String location, UserType type) {
        String passwordHash = passwordEncoder.encode(password);
        return this.userDao.register(email, passwordHash, firstName, lastName, location, type.ordinal());
    }
}