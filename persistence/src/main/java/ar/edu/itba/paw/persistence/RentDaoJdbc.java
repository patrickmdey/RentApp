package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.RentDao;
import ar.edu.itba.paw.models.RentProposal;
import ar.edu.itba.paw.models.RentState;
import ar.edu.itba.paw.models.exceptions.CannotCreateProposalException;
import ar.edu.itba.paw.models.exceptions.CannotEditRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;
import java.util.function.Function;

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


    private StringBuilder sentQueryBuilder(String fields) {
        return new StringBuilder("SELECT " + fields + " FROM rent_proposal WHERE renter_id = ? " +
                "AND state = ? ");
    }

    private StringBuilder receivedQueryBuilder(String fields) {
        return new StringBuilder("SELECT " + fields + " FROM rent_proposal WHERE article_id IN (" +
                "SELECT article.id FROM article WHERE article.owner_id = ?) AND state = ? ");
    }

    @Override
    public Long getReceivedMaxPage(long ownerId, int state) {
        return getMaxPage(ownerId, state, this::receivedQueryBuilder);
    }

    @Override
    public Long getSentMaxPage(long ownerId, int state) {
        return getMaxPage(ownerId, state, this::sentQueryBuilder);
    }

    private Long getMaxPage(long ownerId, int state, Function<String, StringBuilder> queryBuilder) {
        Long size = jdbcTemplate.queryForObject(queryBuilder.apply("COUNT(*)").toString(),
                Long.class, ownerId, state);

        int toSum = (size % OFFSET == 0) ? 0 : 1;
        return (size / OFFSET) + toSum;
    }

    private List<RentProposal> getRequests(long accountId, int state, long page,
                                           Function<String, StringBuilder> queryBuilder) {
        StringBuilder query = queryBuilder.apply("*");
        query.append("ORDER BY start_date DESC, end_date DESC LIMIT ? OFFSET ?");
        return jdbcTemplate.query(query.toString(),
                new Object[]{accountId, state, OFFSET, (page - 1) * OFFSET}, ROW_MAPPER);
    }

    @Override
    public List<RentProposal> ownerRequests(long ownerId, int state, long page) {
        return getRequests(ownerId, state, page, this::receivedQueryBuilder);
    }

    @Override
    public List<RentProposal> sentRequests(long renterId, int state, long page) {
        return getRequests(renterId, state, page, this::sentQueryBuilder);
    }

    @Override
    public Optional<RentProposal> findById(long id) {
        return jdbcTemplate.query("SELECT * FROM rent_proposal WHERE id = ?", new Object[]{id}, ROW_MAPPER).stream().findFirst();
    }

    @Override
    public RentProposal create(String comment, Integer approved, Date startDate, Date endDate, Long articleId, long renterId) {
        Map<String, Object> data = new HashMap<>();
        data.put("message", comment);
        data.put("state", approved);
        data.put("start_date", startDate);
        data.put("end_date", endDate);
        data.put("article_id", articleId);
        data.put("renter_id", renterId);

        try {
            long rentProposalId = jdbcInsert.executeAndReturnKey(data).longValue();
            return new RentProposal(rentProposalId, comment, approved, startDate, endDate, articleId, renterId);
        } catch(Exception e){
            throw new CannotCreateProposalException();
        }
    }

    @Override
    public void updateRequest(long requestId, int state) {
        try {
            jdbcTemplate.update("UPDATE rent_proposal SET state = ? WHERE id = ?", state, requestId);
        } catch(Exception e) {
            throw new CannotEditRequestException();
        }
    }

    @Override
    public boolean hasRented(long renterId, long articleId) {
        return !jdbcTemplate.query(
                "SELECT * FROM rent_proposal WHERE article_id = ? AND renter_id = ? AND state = ?",
                new Object[]{articleId, renterId, RentState.ACCEPTED.ordinal()}, ROW_MAPPER).isEmpty();
    }


    @Override
    public Boolean isPresentSameDate(long renterId, long articleId, Date startDate, Date endDate) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM rent_proposal WHERE renter_id = ? AND" +
                        " article_id = ? AND start_date = ? AND end_date = ?", Long.class, renterId,
                articleId, startDate, endDate) != 0;
    }
}
