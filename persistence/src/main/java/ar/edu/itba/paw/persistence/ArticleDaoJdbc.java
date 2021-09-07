package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.ArticleDao;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class ArticleDaoJdbc implements ArticleDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final SimpleJdbcInsert jdbcInsertCategories;

    private static final RowMapper<Article> ROW_MAPPER =
            (resultSet, rowNum) -> new Article(
                    resultSet.getLong("id"),
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    resultSet.getFloat("price_per_day"),
                    null,
                    resultSet.getLong("owner_id")
            );

    @Autowired
    public ArticleDaoJdbc(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("article")
                .usingGeneratedKeyColumns("id");

        jdbcInsertCategories = new SimpleJdbcInsert(dataSource).withTableName("article_category");

    }

    @Override
    public List<Article> filter(String name) {
        System.out.println(name);
        return jdbcTemplate.query(
                "SELECT * FROM article WHERE LOWER(title) like ?",
                new Object[]{"%" + name.toLowerCase() + "%"},
                ROW_MAPPER);
    }

    @Override
    public List<Article> list() {
        return jdbcTemplate.query("SELECT * FROM article", ROW_MAPPER);
    }

    @Override
    public Optional<Article> findById(long id) {
        return jdbcTemplate.query("SELECT * FROM article WHERE id = ?", new Object[]{id}, ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<Article> create(String title, String description, Float pricePerDay,List<Category> categories, Long idOwner) {
        Map<String, Object> data = new HashMap<>();
        data.put("title", title);
        data.put("description", description);
        data.put("price_per_day", pricePerDay);
        data.put("owner_id", idOwner);

        Long articleId =  jdbcInsert.executeAndReturnKey(data).longValue();
        
        for (Category category: categories) {
            Map<String, Object> categoryData = new HashMap<>();
            categoryData.put("category_id", category.getId());
            categoryData.put("article_id",articleId);
            jdbcInsertCategories.execute(categoryData);
        }

        return Optional.of(new Article(articleId, title, description, pricePerDay, categories, idOwner));
    }
}
