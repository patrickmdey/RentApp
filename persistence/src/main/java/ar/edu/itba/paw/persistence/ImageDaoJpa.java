package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.ImageDao;
import ar.edu.itba.paw.models.DBImage;
import ar.edu.itba.paw.models.exceptions.CannotCreateImageException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class ImageDaoJpa implements ImageDao {

    @PersistenceContext
    EntityManager em;

    @Override
    public Optional<DBImage> findById(long id) {
        return Optional.ofNullable(em.find(DBImage.class, id));
    }

    @Override
    public DBImage create(byte[] img) {
        DBImage image = new DBImage(img);
        try {
            em.persist(image);
            return image;
        } catch(Exception e){
            throw new CannotCreateImageException();
        }
    }

}
