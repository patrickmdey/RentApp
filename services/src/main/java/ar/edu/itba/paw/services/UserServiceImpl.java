package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public Optional<User> findById(Integer id) {
        return this.userDao.findById(id);
    }

    @Override
    public List<User> list() {
        return this.userDao.list();
    }

    @Override
    public User register(String email, String password) {
        return this.userDao.register(email, password);
    }
}