package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    List<Review> getPaged(long articleId, long page);

    void update(int rating, String message, long reviewId);

    Optional<Review> findById(long reviewId);

    Review create(int rating, String message, long articleId, long renterId);

    Long getMaxPage(long articleId);

    boolean hasReviewed(User user, long articleId);
}
