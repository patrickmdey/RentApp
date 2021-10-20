package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.models.DBImage;
import ar.edu.itba.paw.models.Locations;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserType;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Primary
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
        em.persist(user);
        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        final TypedQuery<User> query = em.createQuery("from User as u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        return query.getResultList().stream().findFirst();
    }

    @Override //TODO: se podria borrar
    public void update(long id, String firstName, String lastName, Locations location) {
        User user = em.find(User.class, id);// findById(id).orElseThrow(UserNotFoundException::new);
        //em.getTransaction().begin();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setLocation(location);
        em.persist(user);
        //em.getTransaction().commit();
    }

    @Override
    public void delete(long id) {
        User user = em.find(User.class, id);//findById(id).orElseThrow(UserNotFoundException::new);
        em.remove(user);
    }

    @Override //TODO: se podria borrar
    public void updatePassword(long id, String passwordHash) {
        User user = em.find(User.class, id);//findById(id).orElseThrow(UserNotFoundException::new);
        user.setPassword(passwordHash);
        em.persist(user);
    }
}
