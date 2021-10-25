package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.dao.ReviewDao;
import ar.edu.itba.paw.interfaces.service.ArticleService;
import ar.edu.itba.paw.interfaces.service.ReviewService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.ArticleNotFoundException;
import ar.edu.itba.paw.models.exceptions.ReviewNotFoundException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
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
    public List<Review> getPaged(long articleId, long page) {
        return reviewDao.getPaged(articleId, page);
    }

    @Override
    @Transactional(readOnly = true)
    public int articleRating(long articleId) {
        return Math.round(reviewDao.getAverage(articleId));
    }

    @Override
    @Transactional
    public Review create(int rating, String message, long articleId, long renterId) {
        return reviewDao.create(rating, message, articleId, renterId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getMaxPage(long articleId) {
        return reviewDao.getMaxPage(articleId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasReviewed(User user, Long articleId) {
        if (user == null || articleId == null)
            throw new IllegalArgumentException();

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
