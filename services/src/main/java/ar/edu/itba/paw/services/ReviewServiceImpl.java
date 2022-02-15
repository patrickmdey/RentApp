package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.dao.ReviewDao;
import ar.edu.itba.paw.interfaces.service.ReviewService;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.ReviewNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDao reviewDao;

    @Override
    @Transactional(readOnly = true)
    public List<Review> getPaged(long articleId, Long limit, long page) {
        return reviewDao.getPaged(articleId, limit, page);
    }

    @Override
    @Transactional
    public Review create(int rating, String message, long articleId, long renterId) {
        return reviewDao.create(rating, message, articleId, renterId);
    }

    @Override
    @Transactional(readOnly = true)
    public long getMaxPage(long articleId, Long limit) {
        return reviewDao.getMaxPage(articleId, limit);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasReviewed(User user, long articleId) {
        if (user == null)
            return false;

        return reviewDao.hasReviewed(user.getId(), articleId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Review> findById(long reviewId) {
        return reviewDao.findById(reviewId);
    }

    @Override
    @Transactional
    public void update(int rating, String message, long reviewId) {
        Review review = findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        review.setRating(rating);
        review.setMessage(message);
    }
}