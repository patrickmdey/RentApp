package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.ImageService;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.DBImage;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
    PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> findById(Long id) {
        if(id != null)
            return this.userDao.findById(id);
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.userDao.findByEmail(email);
    }

    @Override
    public List<User> list() {
        return this.userDao.list();
    }

    // TODO: do we need to pass "confirmPassword"? if so, shouldn't we check that it is equal to "password"?
    @Override
    public Optional<User> register(String email, String password, String confirmPassword, String firstName, String lastName, Long location, MultipartFile img, UserType type) {
        String passwordHash = passwordEncoder.encode(password);
        Optional<DBImage> dbImg = imageService.create(img);
        // TODO: raise exception instead of this?
        if (!dbImg.isPresent())
            return Optional.empty();

        return this.userDao.register(email, passwordHash, firstName, lastName, location, dbImg.get().getId(), type.ordinal());
    }

    @Override
    public void update(long id, String firstName, String lastName, Long location, Boolean isOwner) {
        this.userDao.update(id, firstName, lastName, location, (isOwner ? UserType.OWNER : UserType.RENTER).ordinal());
    }

    @Override
    public void delete(long id) {
        this.userDao.delete(id);
    }

    @Override
    public void updatePassword(long id, String password) {
        String passwordHash = passwordEncoder.encode(password);
        userDao.updatePassword(id, passwordHash);

    }
}