package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.ImageDao;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.DBImage;
import ar.edu.itba.paw.models.exceptions.CannotCreateImageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
@Primary
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
        em.persist(image);
        return image;
    }

}
