package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.RentDao;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.RentProposal;
import ar.edu.itba.paw.models.RentState;
import ar.edu.itba.paw.models.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class RentDaoJpa implements RentDao {
    private static final Long RESULTS_PER_PAGE = 4L;

    private static final String OWNER_PARAM = "owner_id";
    private static final String RENTER_PARAM = "renter_id";

    @PersistenceContext
    private EntityManager em;

    private StringBuilder sentQueryBuilder(String fields) {
        return new StringBuilder("SELECT " + fields + " FROM rent_proposal WHERE renter_id = :renter_id " +
                "AND state = :state ");
    }

    private StringBuilder receivedQueryBuilder(String fields) {
        return new StringBuilder("SELECT " + fields + " FROM rent_proposal WHERE article_id IN (" +
                "SELECT article.id FROM article WHERE article.owner_id = :owner_id) AND state = :state ");
    }

    @Override
    public Long getReceivedMaxPage(long ownerId, int state) {
        return getMaxPage(ownerId, state, this::receivedQueryBuilder, OWNER_PARAM);
    }

    @Override
    public Long getSentMaxPage(long ownerId, int state) {
        return getMaxPage(ownerId, state, this::sentQueryBuilder, RENTER_PARAM);
    }

    private Long getMaxPage(long ownerId, int state, Function<String, StringBuilder> queryBuilder, String userParam) {
        Query query = em.createNativeQuery(queryBuilder.apply("COUNT(*)").toString());
        query.setParameter(userParam, ownerId);
        query.setParameter("state", state);

        long size = Long.parseLong(query.getSingleResult().toString());
        int toSum = (size % RESULTS_PER_PAGE == 0) ? 0 : 1;

        return (size / RESULTS_PER_PAGE) + toSum;
    }



    @SuppressWarnings("unchecked")
    private List<RentProposal> getRequests(long accountId, int state, long page,
                                           Function<String, StringBuilder> queryBuilder, String userParam) {
        StringBuilder queryBuild = queryBuilder.apply("id");
        queryBuild.append("ORDER BY start_date DESC, end_date DESC LIMIT :limit OFFSET :offset");

        Query query = em.createNativeQuery(queryBuild.toString());
        query.setParameter("limit", RESULTS_PER_PAGE);
        query.setParameter("offset", (page - 1) * RESULTS_PER_PAGE);
        query.setParameter(userParam, accountId);
        query.setParameter("state", state);

        List<Integer> aux = query.getResultList();

        List<Long> rentProposalIds = aux.stream().mapToLong(Integer::longValue).boxed().collect(Collectors.toList());

        if(rentProposalIds.isEmpty())
            return new ArrayList<>();

        TypedQuery<RentProposal> rentProposalQuery = em.createQuery("FROM RentProposal WHERE" +
                " id IN (:rentProposalIds) ORDER BY startDate DESC, endDate DESC", RentProposal.class);

        rentProposalQuery.setParameter("rentProposalIds", rentProposalIds);

        return rentProposalQuery.getResultList();
    }

    @Override
    public List<RentProposal> ownerRequests(long ownerId, int state, long page) {
        return getRequests(ownerId, state, page, this::receivedQueryBuilder, OWNER_PARAM);
    }

    @Override
    public List<RentProposal> sentRequests(long renterId, int state, long page) {
        return getRequests(renterId, state, page, this::sentQueryBuilder, RENTER_PARAM);
    }

    @Override
    public Optional<RentProposal> findById(long id) {
        return Optional.ofNullable(em.find(RentProposal.class, id));
    }

    @Override
    public RentProposal create(String comment, Integer approved, Date startDate, Date endDate, Long articleId, long renterId) {
        Article article = em.find(Article.class, articleId);
        User renter = em.find(User.class, renterId);

        RentProposal rentProposal = new RentProposal(comment, approved, startDate, endDate);
        rentProposal.setArticle(article);
        rentProposal.setRenter(renter);

        em.persist(rentProposal);
        return rentProposal;
    }


    @Override
    public boolean hasRented(long renterId, long articleId) {
        final TypedQuery<RentProposal> query = em.createQuery(
                "from RentProposal WHERE renter.id = :renter AND article.id = :article " +
                        "AND state = :state", RentProposal.class);

        query.setParameter("renter", renterId);
        query.setParameter("article", articleId);
        query.setParameter("state", RentState.ACCEPTED.ordinal());

        return !query.getResultList().isEmpty();
    }


    @Override
    public Boolean isPresentSameDate(long renterId, long articleId, Date startDate, Date endDate) {
        final TypedQuery<RentProposal> query = em.createQuery(
                "FROM RentProposal WHERE renter.id = :renter AND article.id = :article " +
                        "AND startDate = :startDate AND endDate = :endDate", RentProposal.class);

        query.setParameter("renter", renterId);
        query.setParameter("article", articleId);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);

        return !query.getResultList().isEmpty();
    }
}
