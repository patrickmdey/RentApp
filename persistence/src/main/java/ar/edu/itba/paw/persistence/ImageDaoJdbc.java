package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.ImageDao;
import ar.edu.itba.paw.models.DBImage;
import ar.edu.itba.paw.models.exceptions.CannotCreateImageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Repository
public class ImageDaoJdbc implements ImageDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private static final RowMapper<DBImage> ROW_MAPPER =
            (resultSet, rowNum) -> new DBImage(
                    resultSet.getInt("id"),
                    resultSet.getBytes("data")
            );

    @Autowired
    public ImageDaoJdbc(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("picture")
                .usingGeneratedKeyColumns("id");

    }

    @Override
    public Optional<DBImage> findById(long id) {
        return jdbcTemplate.query("SELECT * FROM picture WHERE id = ?", new Object[] {id}, ROW_MAPPER).stream().findFirst();
    }

    @Override
    public DBImage create(byte[] img) {
        Map<String, Object> data = new HashMap<>();
        data.put("data", img);

        try {
            long imageId = jdbcInsert.executeAndReturnKey(data).longValue();
            return new DBImage(imageId, img);
        } catch(Exception e){
            throw new CannotCreateImageException();
        }
    }
}
