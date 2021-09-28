package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.RentDao;
import ar.edu.itba.paw.models.RentProposal;
import ar.edu.itba.paw.models.RentState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class RentDaoJdbc implements RentDao {
    private static final Long OFFSET = 4L;

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private static final RowMapper<RentProposal> ROW_MAPPER = (resultSet, rowNum) ->
            new RentProposal(resultSet.getLong("id"),
                    resultSet.getString("message"),
                    resultSet.getInt("state"),
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


    private StringBuilder queryBuilder(String fields){
        return new StringBuilder("SELECT " + fields + " FROM rent_proposal WHERE article_id IN (" +
                "SELECT article.id FROM article WHERE article.owner_id = ?) AND state = ?");
    }

    @Override
    public Long getMaxPage(long ownerId, int state) {
        Long size = jdbcTemplate.queryForObject(queryBuilder("COUNT(*)").toString()
                , Long.class, ownerId, state);

        int toSum = (size % OFFSET == 0) ? 0 : 1;

        return (size / OFFSET) + toSum;
    }

    @Override
    public List<RentProposal> list(long ownerId, int state, long page) {
        StringBuilder query = queryBuilder("*");
        query.append("ORDER BY start_date DESC, end_date DESC LIMIT ? OFFSET ?");
        return jdbcTemplate.query(query.toString(),
                new Object[]{ownerId, state, OFFSET, (page - 1) * OFFSET},
                ROW_MAPPER);
    }

    @Override
    public Optional<RentProposal> findById(long id) {
        return jdbcTemplate.query("SELECT * FROM rent_proposal WHERE id = ?", new Object[]{id}, ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<RentProposal> create(String comment, Integer approved, Date startDate, Date endDate, Long articleId, long renterId) {
        Map<String, Object> data = new HashMap<>();
        data.put("message", comment);
        data.put("state", approved);
        data.put("start_date", startDate);
        data.put("end_date", endDate);
        data.put("article_id", articleId);
        data.put("renter_id", renterId);

        long rentProposalId = jdbcInsert.executeAndReturnKey(data).longValue();

        return Optional.of(new RentProposal(rentProposalId, comment, approved, startDate, endDate, articleId, renterId));
    }

    @Override
    public void updateRequest(long requestId, int state) {
        jdbcTemplate.update(
                "UPDATE rent_proposal SET state = ? WHERE id = ?", state, requestId);
    }

    @Override
    public boolean hasRented(long renterId, long articleId) {
        return !jdbcTemplate.query(
                "SELECT * FROM rent_proposal WHERE article_id = ? AND renter_id = ? AND state = ?",
                        new Object[]{articleId, renterId, RentState.ACCEPTED.ordinal()}, ROW_MAPPER).isEmpty();
    }
}
