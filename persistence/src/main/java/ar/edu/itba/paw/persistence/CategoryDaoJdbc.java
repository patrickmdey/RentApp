package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.CategoryDao;
import ar.edu.itba.paw.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CategoryDaoJdbc implements CategoryDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private static final RowMapper<Category> ROW_MAPPER =
            (resultSet, rowNum) -> new Category(
                    resultSet.getInt("id"),
                    resultSet.getString("description")
            );

    private static final RowMapper<Category> WHOLE_ROW_MAPPER = (resultSet, rowNum) ->
            new Category(resultSet.getLong("id"), resultSet.getString("description"));

    @Autowired
    public CategoryDaoJdbc(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("category")
                .usingGeneratedKeyColumns("id");

    }

    @Override
    public List<Category> listByArticle(long articleId) {

        return jdbcTemplate.query("SELECT * FROM category WHERE id IN(" +
                "SELECT DISTINCT category_id FROM article_category WHERE article_id = ?)",
                new Object[] {articleId}, ROW_MAPPER);
    }

    @Override
    public List<Category> listCategories() {
        return jdbcTemplate.query("SELECT * FROM category", WHOLE_ROW_MAPPER);
    }
}
