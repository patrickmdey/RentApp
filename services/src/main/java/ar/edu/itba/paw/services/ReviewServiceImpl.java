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
    public List<Review> getAllArticleReviews(long articleId) {
        List<Review> reviews = reviewDao.list(articleId);
        reviews.forEach(review -> {
            review.setRenter(userService.
                    findById(review.getRenterId()).
                    orElseThrow(RuntimeException::new));
        });
        return reviews;
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
}
