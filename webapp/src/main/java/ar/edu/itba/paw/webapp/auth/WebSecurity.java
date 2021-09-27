package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.ArticleService;
import ar.edu.itba.paw.interfaces.RentService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.interfaces.ReviewService;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.RentProposal;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class WebSecurity {

    @Autowired
    UserService userService;

    @Autowired
    ArticleService articleService;

    @Autowired
    RentService rentService;

    @Autowired
    ReviewService reviewService;

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

        return loggedUser.filter(user -> rentService.hasRented(user, articleId)).isPresent();
    }

    public boolean checkIsReviewOwner(Authentication authentication, long reviewId) {
        Optional<User> loggedUser = getUser(authentication);

        if (!loggedUser.isPresent())
            return false;

        Optional<Review> reviewOpt = reviewService.findById(reviewId);
        return reviewOpt.filter(review -> review.getRenterId() == reviewId).isPresent();
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
