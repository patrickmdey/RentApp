package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.models.DBImage;
import ar.edu.itba.paw.models.Locations;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserType;
import ar.edu.itba.paw.models.exceptions.CannotCreateUserException;
import ar.edu.itba.paw.models.exceptions.CannotEditUserException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class UserDaoJpa implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public User register(String email, String password, String firstName, String lastName, Locations location, DBImage img, UserType type) {
        final User user = new User(email, password, firstName, lastName, location, img, type);
        try {
            em.persist(user);
            return user;
        } catch (Exception e) {
            throw new CannotCreateUserException();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        final TypedQuery<User> query = em.createQuery("from User as u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public void delete(long id) {
        User user = em.find(User.class, id);
        if (user == null)
            throw new UserNotFoundException();
        
        try {
            em.remove(user);
        } catch (Exception e) {
            throw new CannotEditUserException();
        }
    }
}
