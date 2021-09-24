package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.RentDao;
import ar.edu.itba.paw.interfaces.ReviewDao;
import ar.edu.itba.paw.models.RentProposal;
import ar.edu.itba.paw.models.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class ReviewDaoJdbc implements ReviewDao {
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

    @Override
    public List<Review> list(long articleId) {
        return jdbcTemplate.query("SELECT * FROM review WHERE article_id = ? ORDER BY created_at DESC",
                new Object[]{articleId},
                ROW_MAPPER);
    }

    @Override
    public Optional<Review> create(int rating, String message, long articleId, long renterId) {
        Map<String, Object> data = new HashMap<>();
        data.put("rating", rating);
        data.put("message", message);
        data.put("article_id", articleId);
        data.put("renter_id", renterId);
        Date createdAt = new Date(System.currentTimeMillis());
        data.put("created_at", createdAt);

        long reviewId = jdbcInsert.executeAndReturnKey(data).longValue();
        return Optional.of(new Review(reviewId, rating, message, articleId, renterId, createdAt));
    }

    @Override
    public Optional<Review> findById(long reviewId) {
        return jdbcTemplate.query("SELECT * FROM review WHERE id = ?", new Object[]{reviewId}, ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public int update(int rating, String message, long reviewId) {
        jdbcTemplate.update("UPDATE review SET rating = ? WHERE id = ? ", rating, reviewId);
        jdbcTemplate.update("UPDATE review SET message = ? WHERE id = ? ", message, reviewId);
        return 1;
    }
}
