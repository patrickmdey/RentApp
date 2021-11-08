package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.RentDao;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.RentProposal;
import ar.edu.itba.paw.models.RentState;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.ArticleNotFoundException;
import ar.edu.itba.paw.models.exceptions.CannotCreateProposalException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import java.time.LocalDate;

@Repository
public class RentDaoJpa implements RentDao {
    private static final long RESULTS_PER_PAGE = 4L;

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
    public long getReceivedMaxPage(long ownerId, int state) {
        return getMaxPage(ownerId, state, this::receivedQueryBuilder, OWNER_PARAM);
    }

    @Override
    public long getSentMaxPage(long ownerId, int state) {
        return getMaxPage(ownerId, state, this::sentQueryBuilder, RENTER_PARAM);
    }

    private long getMaxPage(long ownerId, int state, Function<String, StringBuilder> queryBuilder, String userParam) {
        Query query = em.createNativeQuery(queryBuilder.apply("COUNT(*)").toString());
        query.setParameter(userParam, ownerId);
        query.setParameter("state", state);

        long size = Long.parseLong(query.getSingleResult().toString());
        int toSum = (size % RESULTS_PER_PAGE == 0) ? 0 : 1;

        return (size / RESULTS_PER_PAGE) + toSum;
    }



    private List<RentProposal> getRequests(long accountId, int state, long page,
                                           Function<String, StringBuilder> queryBuilder, String userParam) {
        StringBuilder queryBuild = queryBuilder.apply("id");
        queryBuild.append("ORDER BY seen, start_date DESC, end_date DESC LIMIT :limit OFFSET :offset");

        Query query = em.createNativeQuery(queryBuild.toString());
        query.setParameter("limit", RESULTS_PER_PAGE);
        query.setParameter("offset", (page - 1) * RESULTS_PER_PAGE);
        query.setParameter(userParam, accountId);
        query.setParameter("state", state);

        @SuppressWarnings("unchecked")
        List<Long> rentProposalIds = ((List<Number>) query.getResultList()).stream().mapToLong(Number::longValue).boxed().collect(Collectors.toList());


        if(rentProposalIds.isEmpty())
            return Collections.emptyList();

        TypedQuery<RentProposal> rentProposalQuery = em.createQuery("FROM RentProposal WHERE" +
                " id IN (:rentProposalIds) ORDER BY seen, startDate DESC, endDate DESC", RentProposal.class);

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
    public RentProposal create(String comment, int approved, LocalDate startDate, LocalDate endDate, long articleId, long renterId) {
        Article article = em.find(Article.class, articleId);
        if (article == null)
            throw new ArticleNotFoundException();

        User renter = em.find(User.class, renterId);
        if (renter == null)
            throw new UserNotFoundException();

        RentProposal rentProposal = new RentProposal(comment, approved, startDate, endDate);
        rentProposal.setArticle(article);
        rentProposal.setRenter(renter);

        try {
            em.persist(rentProposal);
            return rentProposal;
        } catch (Exception e) {
            throw new CannotCreateProposalException();
        }
    }


    @Override
    public boolean hasRented(long renterId, long articleId) {
        final TypedQuery<Long> query = em.createQuery("SELECT count(r) from RentProposal as r " +
                        "WHERE r.renter.id = :renter AND r.article.id = :article " +
                        "AND r.state = :state", Long.class);

        query.setParameter("renter", renterId);
        query.setParameter("article", articleId);
        query.setParameter("state", RentState.ACCEPTED.ordinal());

        return Long.parseLong(query.getSingleResult().toString()) != 0;
    }


    @Override
    public boolean isPresentSameDate(long renterId, long articleId, LocalDate startDate, LocalDate endDate) {
        final TypedQuery<Long> query = em.createQuery("SELECT count(r) FROM RentProposal as r " +
                "WHERE r.renter.id = :renter AND r.article.id = :article " +
                        "AND r.startDate = :startDate AND r.endDate = :endDate", Long.class);

        query.setParameter("renter", renterId);
        query.setParameter("article", articleId);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);

        return Long.parseLong(query.getSingleResult().toString()) != 0;
    }
}
