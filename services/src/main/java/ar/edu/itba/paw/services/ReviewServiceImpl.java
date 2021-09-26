package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.RentProposal;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Override
    public List<Review> getPaged(long articleId, long page) {
        List<Review> reviews = reviewDao.getPaged(articleId, page);
        reviews.forEach(review -> {
            review.setRenter(userService.
                    findById(review.getRenterId()).
                    orElseThrow(RuntimeException::new));
        });
        return reviews;
    }

    @Override
    public int articleRating(long articleId) {
        List<Review> allReviews = reviewDao.getAll(articleId);
        if (allReviews.size() == 0)
            return 0;
        return allReviews.stream().mapToInt(Review::getRating).sum() / allReviews.size();
    }

    @Override
    public Optional<Review> create(int rating, String message, long articleId, long renterId) {
        Optional<Article> article = articleService.findById(articleId);
        if (article.isPresent()) {
            Optional<Review> review = reviewDao.create(rating, message, articleId, renterId);
            review.ifPresent(value -> value.setRenter(userService.findById(renterId).orElseThrow(RuntimeException::new)));
        }
        return Optional.empty();
    }

    @Override
    public Long getMaxPage(long articleId) {
        return reviewDao.getMaxPage(articleId);
    }

    @Override
    public Optional<Review> findById(long reviewId) {
        return reviewDao.findById(reviewId);
    }

    @Override
    public int update(int rating, String message, long reviewId) {
        return reviewDao.update(rating, message, reviewId);
    }

}
