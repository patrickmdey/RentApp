package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.interfaces.dao.RentDao;
import ar.edu.itba.paw.interfaces.service.ArticleService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.ArticleNotFoundException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@Primary
public class RentDaoJpa implements RentDao {
    private static final Long RESULTS_PER_PAGE = 4L;

    @PersistenceContext
    private EntityManager em;

    private StringBuilder sentQueryBuilder(String fields) {
        return new StringBuilder("SELECT " + fields + " FROM rent_proposal WHERE renter_id = :renter " +
                "AND state = :state ");
    }

    private StringBuilder receivedQueryBuilder(String fields) {
        return new StringBuilder("SELECT " + fields + " FROM rent_proposal WHERE article_id IN (" +
                "SELECT article.id FROM article WHERE article.owner_id = :owner_id) AND state = :state ");
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
        Query query = em.createNativeQuery(queryBuilder.apply("COUNT(*)").toString());
        query.setParameter("owner_id", ownerId);
        query.setParameter("state", state);

        Long size = (Long) query.getSingleResult();
        int toSum = (size % RESULTS_PER_PAGE == 0) ? 0 : 1;

        return (size / RESULTS_PER_PAGE) + toSum;
    }



    @SuppressWarnings("unchecked")
    private List<RentProposal> getRequests(long accountId, int state, long page,
                                           Function<String, StringBuilder> queryBuilder) {
        StringBuilder queryBuild = queryBuilder.apply("id");
        queryBuild.append("ORDER BY start_date DESC, end_date DESC LIMIT :limit OFFSET :offset");

        Query query = em.createNativeQuery(queryBuilder.toString());
        query.setParameter("limit", RESULTS_PER_PAGE);
        query.setParameter("offset", page * RESULTS_PER_PAGE);
        query.setParameter("owner_id", accountId);
        query.setParameter("state", state);



        List<Integer> aux = query.getResultList();

        List<Long> rentProposalIds = aux.stream().mapToLong(Integer::longValue).boxed().collect(Collectors.toList());

        TypedQuery<RentProposal> rentProposalQuery = em.createQuery("FROM RentProposal WHERE" +
                " id IN (:rentProposalIds) ORDER BY startDate DESC, endDate DESC", RentProposal.class);

        query.setParameter("rentProposalIds", rentProposalIds);

        return rentProposalQuery.getResultList();
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
        return Optional.ofNullable(em.find(RentProposal.class, id));
    }

    @Override
    public RentProposal create(String comment, Integer approved, Date startDate, Date endDate, Long articleId, long renterId) {
        Article article = em.find(Article.class, articleId);// articleService.findById(articleId).orElseThrow(ArticleNotFoundException::new);
        User renter = em.find(User.class, renterId);//userService.findById(renterId).orElseThrow(UserNotFoundException::new);

        RentProposal rentProposal = new RentProposal(comment, approved, startDate, endDate);

        rentProposal.setArticle(article);
        rentProposal.setRenter(renter);

        em.persist(rentProposal);
        return rentProposal;
    }


    @Override
    public boolean hasRented(long renterId, long articleId) {
        final TypedQuery<RentProposal> query = em.createQuery(
                "FROM RentProposal WHERE renter.id = :renter AND article.id = :article " +
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
