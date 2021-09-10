package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.ArticlePictureDao;
import ar.edu.itba.paw.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ArticlePictureDaoJdbc implements ArticlePictureDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private static final RowMapper<Long> ROW_MAPPER =
            (resultSet, rowNum) -> resultSet.getLong("picture_id");

    @Autowired
    public ArticlePictureDaoJdbc(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("article_picture");

    }

    @Override
    public boolean addPictureToArticle(long pictureId, long articleId) {
        return false;
    }

    @Override
    public List<Long> listArticlePicturesUrl(long articleId) {
        return jdbcTemplate.query("SELECT picture_id FROM article_picture WHERE article_id = ?",
                new Object[]{articleId}, ROW_MAPPER);
    }


}
