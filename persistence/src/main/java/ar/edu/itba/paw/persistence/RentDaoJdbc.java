package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.RentDao;
import ar.edu.itba.paw.models.RentProposal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class RentDaoJdbc implements RentDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private static final RowMapper<RentProposal> ROW_MAPPER = (resultSet, rowNum) ->
            new RentProposal(resultSet.getLong("id"),
                    resultSet.getString("message"),
                    resultSet.getBoolean("approved"),
                    resultSet.getDate("start_date"),
                    resultSet.getDate("end_date"),
                    resultSet.getInt("article_id"),
                    resultSet.getInt("renter_id"));

    @Autowired
    public RentDaoJdbc(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("rent_proposal")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<RentProposal> list(long ownerId) {
        return jdbcTemplate.query("SELECT * FROM rent_proposal WHERE article_id IN (" +
                "SELECT article.id FROM article WHERE article.owner_id = ?)", new Object[]{ownerId}, ROW_MAPPER);
    }

    @Override
    public Optional<RentProposal> findById(long id) {
        return jdbcTemplate.query("SELECT * FROM rent_proposal WHERE id = ?", new Object[]{id}, ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<RentProposal> create(String comment, Boolean approved, Date startDate, Date endDate, Integer articleId, long renterId) {
        Map<String, Object> data = new HashMap<>();
        data.put("message", comment);
        data.put("approved", approved);
        data.put("start_date", startDate);
        data.put("end_date", endDate);
        data.put("article_id", articleId);
        data.put("renter_id", renterId);

        long rentProposalId = jdbcInsert.executeAndReturnKey(data).longValue();

        return Optional.of(new RentProposal(rentProposalId, comment, approved, startDate, endDate, articleId, renterId));
    }

    @Override
    public void acceptRequest(long requestId) {
        jdbcTemplate.update("UPDATE rent_proposal SET approved = true WHERE id = ?", requestId);
    }

    @Override
    public void deleteRequest(long requestId) {
        jdbcTemplate.update("DELETE FROM rent_proposal WHERE id = ?", requestId);
    }
}
