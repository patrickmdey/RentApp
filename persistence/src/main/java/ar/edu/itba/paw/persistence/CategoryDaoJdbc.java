package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.CategoryDao;
import ar.edu.itba.paw.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class CategoryDaoJdbc implements CategoryDao {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Category> ROW_MAPPER =
            (resultSet, rowNum) -> new Category(
                    resultSet.getInt("id"),
                    resultSet.getString("description")
            );

    @Autowired
    public CategoryDaoJdbc(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Category> listAll() {
        return jdbcTemplate.query("SELECT * FROM category", ROW_MAPPER);
    }

    @Override
    public Optional<Category> findById(Long category) {
        return jdbcTemplate.query("SELECT * FROM category WHERE id = ?", new Object[] {category},
                ROW_MAPPER).stream().findFirst();
    }
}
