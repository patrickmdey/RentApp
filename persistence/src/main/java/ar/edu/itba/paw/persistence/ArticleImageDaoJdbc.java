package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.ArticleImageDao;
import ar.edu.itba.paw.models.DBImage;
import ar.edu.itba.paw.models.exceptions.CannotCreateArticleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ArticleImageDaoJdbc implements ArticleImageDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<Long> ROW_MAPPER =
            (resultSet, rowNum) -> (long) resultSet.getInt("picture_id");

    @Autowired
    public ArticleImageDaoJdbc(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("article_picture")
                .usingGeneratedKeyColumns("id");
    }


    @Override
    public List<Long> findFromArticle(long articleId) {
        return jdbcTemplate.query("SELECT DISTINCT picture_id FROM article_picture WHERE article_id = ?",
                new Object[] {articleId}, ROW_MAPPER);
    }

    @Override
    public DBImage addToArticle(long articleId, DBImage image) {
        Map<String, Object> categoryData = new HashMap<>();
        categoryData.put("picture_id", image.getId());
        categoryData.put("article_id", articleId);
        try {
            jdbcInsert.execute(categoryData);
        } catch (Exception e) {
            throw new CannotCreateArticleException();
        }
        return image;
    }
}
