package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.service.EmailService;
import ar.edu.itba.paw.interfaces.service.ImageService;
import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.models.DBImage;
import ar.edu.itba.paw.models.Locations;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserType;
import ar.edu.itba.paw.models.exceptions.EmailAlreadyInUseException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
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
    public Optional<User> findById(Long id) {
        if (id == null)
            return Optional.empty();

        return userDao.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public List<User> list() {
        return userDao.list();
    }

    @Override
    @Transactional
    public Optional<User> register(String email, String password, String firstName, String lastName, Long location, MultipartFile img, UserType type) {
        String passwordHash = passwordEncoder.encode(password);
        Optional<DBImage> dbImg = imageService.create(img);
        // TODO: raise exception instead of this?
        if (!dbImg.isPresent())
            return Optional.empty();

        Optional<User> user = userDao.register(email, passwordHash, firstName, lastName, Locations.values()[Math.toIntExact(location)], dbImg.get().getId(), type);
        if (!user.isPresent())
            return Optional.empty();

        emailService.sendNewUserMail(user.get());

        return user;
    }

    @Override
    public void update(long id, String firstName, String lastName, Long location, Boolean isOwner) {
        userDao.update(id, firstName, lastName, Locations.values()[Math.toIntExact(location)], (isOwner ? UserType.OWNER : UserType.RENTER));
    }

    @Override
    public void delete(long id) {
        userDao.delete(id);
    }

    @Override
    public void updatePassword(long id, String password) {
        String passwordHash = passwordEncoder.encode(password);
        userDao.updatePassword(id, passwordHash);
    }
}