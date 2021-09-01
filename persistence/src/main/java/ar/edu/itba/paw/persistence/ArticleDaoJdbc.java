package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.ArticleDao;
import ar.edu.itba.paw.models.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ArticleDaoJdbc implements ArticleDao {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private static final RowMapper<Article> ROW_MAPPER =
            (resultSet, rowNum) -> new Article(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    resultSet.getFloat("price_per_day"),
                    resultSet.getInt("id_category"),
                    resultSet.getInt("id_owner")
            );

    @Autowired
    public ArticleDaoJdbc(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("Articles")
                .usingGeneratedKeyColumns("id");

    }

    @Override
    public List<Article> filter(String name) {
        return jdbcTemplate.query(
                "SELECT * FROM \"Articles\" WHERE ? like LOWER(title)",
                new Object[]{name.toLowerCase()},
                ROW_MAPPER);
    }

    @Override
    public List<Article> list() {
        return jdbcTemplate.query("SELECT * FROM ARTICLES", ROW_MAPPER);
    }

    @Override
    public Optional<Article> findById(long id) {
        return jdbcTemplate.query("SELECT * FROM ARTICLES WHERE ID = ?", new Object[]{id}, ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Article create(String title, String description, Float pricePerDay, Integer idCategory, Integer idOwner) {
        Map<String, Object> data = new HashMap<>();
        data.put("title", title);
        data.put("description", description);
        data.put("price_per_day", pricePerDay);
        data.put("id_category", idCategory);
        data.put("id_owner", idOwner);
        Integer articleId = jdbcInsert.execute(data);

        return new Article(articleId, title, description, pricePerDay, idCategory, idOwner);
    }
}
