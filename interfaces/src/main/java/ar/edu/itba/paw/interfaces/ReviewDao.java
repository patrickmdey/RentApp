package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewDao {
    List<Review> list(long articleId);

    Optional<Review> create(int rating, String message, long articleId, long renterId);
}
