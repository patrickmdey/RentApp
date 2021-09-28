package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewDao {
    List<Review> getPaged(long articleId, long page);

    List<Review> getAll(long articleId);

    void update(int rating, String message, long reviewId);

    Optional<Review> findById(long reviewId);

    Optional<Review> create(int rating, String message, long articleId, long renterId);

    Long getMaxPage(long articleId);

    boolean hasReviewed(long userId, long articleId);
}
