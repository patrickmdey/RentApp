package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.ReviewDao;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.ArticleNotFoundException;
import ar.edu.itba.paw.models.exceptions.CannotCreateReviewException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.springframework.stereotype.Repository;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ReviewDaoJpa implements ReviewDao {

    private final static long RESULTS_PER_PAGE = 3L;

    @PersistenceContext
    private EntityManager em;

    private StringBuilder queryBuilder(String fields) {
        return new StringBuilder("SELECT " + fields + " FROM review WHERE article_id = :article_id ");
    }

    @Override
    public List<Review> getPaged(long articleId, Long limit, long page) {
        StringBuilder queryBuilder = queryBuilder("id");
        queryBuilder.append(" ORDER BY created_at DESC LIMIT :limit OFFSET :offset");

        Query query = em.createNativeQuery(queryBuilder.toString());
        query.setParameter("article_id", articleId);

        long resultsPerPage = limit == null ? RESULTS_PER_PAGE : limit;
        query.setParameter("limit", resultsPerPage);
        query.setParameter("offset", (page - 1) * resultsPerPage);

        @SuppressWarnings("unchecked")
        List<Long> reviewIds = ((List<Number>) query.getResultList()).stream().mapToLong(Number::longValue).boxed().collect(Collectors.toList());

        if (reviewIds.isEmpty())
            return Collections.emptyList();

        TypedQuery<Review> reviewQuery = em.createQuery("from Review " +
                "WHERE id IN (:reviewIds) ORDER BY createdAt DESC", Review.class);

        reviewQuery.setParameter("reviewIds", reviewIds);

        return reviewQuery.getResultList();
    }

    @Override
    public long getMaxPage(long articleId, Long limit) {
        Query query = em.createNativeQuery(queryBuilder("COUNT(*)").toString());
        query.setParameter("article_id", articleId);
        long size = Long.parseLong(query.getSingleResult().toString());

        long resultsPerPage = limit == null ? RESULTS_PER_PAGE : limit;
        int toSum = (size % resultsPerPage == 0) ? 0 : 1;
        return (size / resultsPerPage) + toSum;
    }

    @Override
    public boolean hasReviewed(long userId, long articleId) {
        final TypedQuery<Long> query = em.createQuery("SELECT count(r) from Review as r " +
                "WHERE r.renter.id = :renter AND r.article.id = :article_id", Long.class);

        query.setParameter("renter", userId);
        query.setParameter("article_id", articleId);

        return Long.parseLong(query.getSingleResult().toString()) != 0;
    }

    @Override
    public Review create(int rating, String message, long articleId, long renterId) {
        Article article = em.find(Article.class, articleId);
        if(article == null)
            throw new ArticleNotFoundException();
        
        User renter = em.find(User.class, renterId);
        if (renter == null)
            throw new UserNotFoundException();

        Review review = new Review(rating, message, LocalDate.now());
        review.setArticle(article);
        review.setRenter(renter);

        try {
            em.persist(review);
            return review;
        } catch (Exception e) {
            throw new CannotCreateReviewException();
        }
    }

    @Override
    public Optional<Review> findById(long reviewId) {
        return Optional.ofNullable(em.find(Review.class, reviewId));
    }

}
