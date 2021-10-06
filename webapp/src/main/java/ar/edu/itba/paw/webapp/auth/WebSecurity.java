package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.service.ArticleService;
import ar.edu.itba.paw.interfaces.service.RentService;
import ar.edu.itba.paw.interfaces.service.ReviewService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.RentProposal;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class WebSecurity {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private RentService rentService;

    @Autowired
    private ReviewService reviewService;

    public boolean checkIsArticleOwner(Authentication authentication, long articleId) {
        Optional<User> loggedUser = getUser(authentication);

        return loggedUser.filter(user -> isArticleOwner(user, articleId)).isPresent();

    }

    public boolean checkIsRentOwner(Authentication authentication, long rentId) {
        Optional<User> loggedUser = getUser(authentication);

        if (!loggedUser.isPresent())
            return false;

        Optional<RentProposal> proposal = rentService.findById(rentId);

        return proposal.filter(rentProposal -> isArticleOwner(loggedUser.get(), rentProposal.getArticleId())).isPresent();

    }

    public boolean checkCanReview(Authentication authentication, long articleId) {
        Optional<User> loggedUser = getUser(authentication);
        if (!loggedUser.isPresent())
            return false;

        return rentService.hasRented(loggedUser.get(), articleId) && !reviewService.hasReviewed(loggedUser.get(), articleId);

    }

    public boolean checkIsReviewOwner(Authentication authentication, long reviewId) {
        Optional<User> loggedUser = getUser(authentication);

        if (!loggedUser.isPresent())
            return false;

        Optional<Review> reviewOpt = reviewService.findById(reviewId);
        return reviewOpt.filter(review -> review.getRenterId() == loggedUser.get().getId()).isPresent();
    }

    private Optional<User> getUser(Authentication authentication) {
        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userService.findByEmail(userDetails.getUsername());
        } catch (ClassCastException e) {
            return Optional.empty();
        }
    }

    private boolean isArticleOwner(User user, Long articleId) {
        Optional<Article> article = articleService.findById(articleId);

        return article.isPresent() && user.getId() == article.get().getIdOwner();
    }
}
