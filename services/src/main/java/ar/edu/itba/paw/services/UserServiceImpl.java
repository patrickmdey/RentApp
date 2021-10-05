package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.interfaces.ImageService;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.DBImage;
import ar.edu.itba.paw.models.Locations;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        if (id != null)
            return userDao.findById(id);
        return Optional.empty();
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
            throw new RuntimeException(); //EmailAlreadyInUseException

        Map<String, String> values = new HashMap<>();
        values.put("ownerName", user.get().getFirstName());
        values.put("ownerEmail", user.get().getEmail());
        emailService.sendNewUserMail(user.get().getEmail(), values);

        return user;
    }

    @Override
    public void update(long id, String firstName, String lastName, Long location, Boolean isOwner) {
        userDao.update(id, firstName, lastName, Locations.values()[Math.toIntExact(location)], (isOwner ? UserType.OWNER : UserType.RENTER).ordinal());
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