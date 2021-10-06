package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.ReviewDao;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.exceptions.CannotCreateReviewException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class ReviewDaoJdbc implements ReviewDao {
    private static final Long OFFSET = 3L;

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private static final RowMapper<Review> ROW_MAPPER = (resultSet, rowNum) ->
            new Review(
                    resultSet.getLong("id"),
                    resultSet.getInt("rating"),
                    resultSet.getString("message"),
                    resultSet.getInt("article_id"),
                    resultSet.getInt("renter_id"),
                    resultSet.getTimestamp("created_at"));

    @Autowired
    public ReviewDaoJdbc(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("review")
                .usingGeneratedKeyColumns("id");
    }

    private StringBuilder queryBuilder(String fields) {
        return new StringBuilder("SELECT " + fields + "FROM review WHERE article_id = ? ");
    }

    @Override
    public float getAverage(long articleId) {
        return jdbcTemplate.queryForObject(queryBuilder("COALESCE(AVG(rating), 0)").toString(),
                Float.class, articleId);
    }

    @Override
    public List<Review> getPaged(long articleId, long page) {
        StringBuilder query = queryBuilder("*");
        query.append("ORDER BY created_at DESC LIMIT ? OFFSET ?");
        return jdbcTemplate.query(query.toString(),
                new Object[]{articleId, OFFSET, (page - 1) * OFFSET},
                ROW_MAPPER);
    }

    @Override
    public Long getMaxPage(long articleId) {
        Long size = jdbcTemplate.queryForObject(queryBuilder("COUNT(*)").toString()
                , Long.class, articleId);

        int toSum = (size % OFFSET == 0) ? 0 : 1;

        return (size / OFFSET) + toSum;
    }

    @Override
    public boolean hasReviewed(long userId, long articleId) {
        return !jdbcTemplate.query(
                "SELECT * FROM review WHERE article_id = ? AND renter_id = ?",
                new Object[]{articleId, userId}, ROW_MAPPER).isEmpty();
    }

    @Override
    public Review create(int rating, String message, long articleId, long renterId) {
        Map<String, Object> data = new HashMap<>();
        data.put("rating", rating);
        data.put("message", message);
        data.put("article_id", articleId);
        data.put("renter_id", renterId);
        Date createdAt = new Date(System.currentTimeMillis());
        data.put("created_at", createdAt);

        try {
            long reviewId = jdbcInsert.executeAndReturnKey(data).longValue();
            return new Review(reviewId, rating, message, articleId, renterId, createdAt);
        } catch(Exception e){
            throw new CannotCreateReviewException();
        }
    }

    @Override
    public Optional<Review> findById(long reviewId) {
        return jdbcTemplate.query("SELECT * FROM review WHERE id = ?", new Object[]{reviewId}, ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public void update(int rating, String message, long reviewId) {
        jdbcTemplate.update("UPDATE review SET rating = ? WHERE id = ? ", rating, reviewId);
        jdbcTemplate.update("UPDATE review SET message = ? WHERE id = ? ", message, reviewId);
    }
}
