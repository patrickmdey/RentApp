package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.ArticleCategoryDao;
import ar.edu.itba.paw.interfaces.ArticleDao;
import ar.edu.itba.paw.interfaces.CategoryDao;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Category;
import ar.edu.itba.paw.models.OrderOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class ArticleDaoJdbc implements ArticleDao {

    private static final Long OFFSET = 4L;

    @Autowired
    ArticleCategoryDao articleCategoryDao;

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<Article> ROW_MAPPER =
            (resultSet, rowNum) -> new Article(
                    resultSet.getLong("id"),
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    resultSet.getFloat("price_per_day"),
                    resultSet.getLong("owner_id")
            );

    @Autowired
    public ArticleDaoJdbc(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("article")
                .usingGeneratedKeyColumns("id");

    }

    private StringBuilder queryBuilder(List<Object> params, String fields, String name, Long category, Long user, Long location){
        StringBuilder query = new StringBuilder("SELECT "+ fields + " FROM article WHERE true ");

        if (name != null && name.length() > 0) {
            query.append(" AND LOWER(article.title) LIKE ? ");
            params.add("%" + name.toLowerCase() + "%");
        }

        if (user != null) {
            query.append(" AND owner_id = ? ");
            params.add(user);
        }

        if (category != null) {
            query.append(" AND article.id IN (SELECT article_id FROM article_category " +
                    "WHERE category_id = ?) ");
            params.add(category);
        }

        if(location != null){
            query.append(" AND owner_id IN (SELECT account.id FROM account " +
                    "WHERE account.location = ?) ");
            params.add(location);
        }

        return query;
    }

    @Override
    public List<Article> filter(String name, Long category, String orderBy, Long user, Long location, Long page) {
        ArrayList<Object> params = new ArrayList<>();

        StringBuilder query = queryBuilder(params, "*", name, category, user, location);

        if (orderBy != null) {
            query.append(" ORDER BY ");
            query.append(orderBy);
        } else {
            query.append(" ORDER BY title");
        }

        query.append(" LIMIT ? OFFSET ?");
        params.add(OFFSET);
        params.add((page - 1) * OFFSET);


        return jdbcTemplate.query(query.toString(), params.toArray(), ROW_MAPPER);
    }

    @Override
    public Long getMaxPage(String name, Long category, Long user, Long location) {
        ArrayList<Object> params = new ArrayList<>();
        StringBuilder query = queryBuilder(params, "COUNT(*)", name, category, user, location);

        Long size = jdbcTemplate.queryForObject(query.toString(), Long.class, params.toArray());

        int toSum = (size % OFFSET == 0) ? 0 : 1;

        return (size / OFFSET) + toSum;
    }

    @Override
    public Optional<Article> findById(long id) {
        Optional<Article> optArticle = jdbcTemplate.query("SELECT * FROM article WHERE id = ?",
                        new Object[]{id}, ROW_MAPPER)
                .stream()
                .findFirst();

        if (!optArticle.isPresent())
            return Optional.empty();

        Article article = optArticle.get();

        return Optional.of(article);
    }


    @Override
    public Optional<Article> createArticle(String title, String description, Float pricePerDay, long idOwner) {
        Map<String, Object> data = new HashMap<>();
        data.put("title", title);
        data.put("description", description);
        data.put("price_per_day", pricePerDay);
        data.put("owner_id", idOwner);

        long articleId = jdbcInsert.executeAndReturnKey(data).longValue();

        return Optional.of(new Article(articleId, title, description, pricePerDay, idOwner));
    }
}
