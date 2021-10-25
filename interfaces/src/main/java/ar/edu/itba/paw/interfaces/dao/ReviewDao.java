package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.models.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewDao {

    List<Review> getPaged(long articleId, long page);

    Optional<Review> findById(long reviewId);

    Review create(int rating, String message, long articleId, long renterId);

    Long getMaxPage(long articleId);

    boolean hasReviewed(long userId, long articleId);
}
