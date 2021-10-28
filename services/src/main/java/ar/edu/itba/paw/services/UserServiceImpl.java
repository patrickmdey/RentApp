package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.interfaces.service.EmailService;
import ar.edu.itba.paw.interfaces.service.ImageService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.models.DBImage;
import ar.edu.itba.paw.models.Locations;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserType;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private ImageService imageService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        if (id == null)
            return Optional.empty();

        return userDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    @Transactional
    public User register(String email, String password, String firstName, String lastName, Long location, MultipartFile img, UserType type) {
        String passwordHash = passwordEncoder.encode(password);
        DBImage dbImg = imageService.create(img);

        User user = userDao.register(email, passwordHash, firstName, lastName, Locations.values()[Math.toIntExact(location)], dbImg, type);
        emailService.sendNewUserMail(user);

        return user;
    }

    @Override
    @Transactional
    public void update(long id, String firstName, String lastName, Long location) {
        User user = findById(id).orElseThrow(UserNotFoundException::new);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setLocation(Locations.values()[Math.toIntExact(location)]);
    }

    @Override
    @Transactional
    public void delete(long id) {
        userDao.delete(id);
    }

    @Override
    @Transactional
    public void updatePassword(long id, String password) {
        String passwordHash = passwordEncoder.encode(password);
        User user = findById(id).orElseThrow(UserNotFoundException::new);
        user.setPassword(passwordHash);
    }
}