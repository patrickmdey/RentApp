package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.exceptions.ArticleNotFoundException;
import ar.edu.itba.paw.models.exceptions.ReviewNotFoundException;
import ar.edu.itba.paw.interfaces.service.ArticleService;
import ar.edu.itba.paw.interfaces.service.ReviewService;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.webapp.forms.ReviewForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private LoggedUserAdvice userAdvice;

    private final Logger reviewLogger = LoggerFactory.getLogger(ReviewController.class);

    @RequestMapping(value = "/{articleId}/create")
    public ModelAndView publishReview(@ModelAttribute("reviewForm") ReviewForm reviewForm, @PathVariable("articleId") Long articleId) {
        ModelAndView mav = new ModelAndView("review/create");
        mav.addObject("rating", new Integer[]{1, 2, 3, 4, 5});
        mav.addObject("article", articleService.findById(articleId).orElseThrow(ArticleNotFoundException::new));
        return mav;
    }

    @RequestMapping(value = "/{articleId}/create", method = RequestMethod.POST)
    @PreAuthorize("@webSecurity.checkCanReview(authentication,#articleId)")
    public ModelAndView createReview(@Valid @ModelAttribute("reviewForm") ReviewForm reviewForm,
                                     BindingResult errors, @PathVariable("articleId") Long articleId) {
        if (errors.hasErrors()) {
            return publishReview(reviewForm, articleId);
        }
        reviewLogger.info("publishing review for article with id '{}' with params --> rating: {}, message: {}",
                articleId, reviewForm.getRating(), reviewForm.getMessage());
        reviewService.create(reviewForm.getRating(), reviewForm.getMessage(), articleId, userAdvice.loggedUser().getId());
        return new ModelAndView("redirect:/article/" + articleId);
    }

    @RequestMapping(value = "/{reviewId}/edit", method = RequestMethod.GET)
    @PreAuthorize("@webSecurity.checkIsReviewOwner(authentication,#reviewId)")
    public ModelAndView editReview(@ModelAttribute("reviewForm") ReviewForm reviewForm,
                                   @PathVariable("reviewId") Long reviewId) {
        final ModelAndView mav = new ModelAndView("review/edit");
        Review review = reviewService.findById(reviewId).orElseThrow(ReviewNotFoundException::new);

        mav.addObject("rating", new Integer[]{1, 2, 3, 4, 5});
        mav.addObject("article", articleService.findById(review.getArticleId()).orElseThrow(ArticleNotFoundException::new));
        populateReviewForm(review, reviewForm);
        return mav;
    }

    @RequestMapping(value = "/{reviewId}/edit", method = RequestMethod.POST)
    @PreAuthorize("@webSecurity.checkIsReviewOwner(authentication,#reviewId)")
    public ModelAndView updateReview(@Valid @ModelAttribute("reviewForm") ReviewForm reviewForm,
                                     BindingResult errors,
                                     @PathVariable("reviewId") Long reviewId) {

        if (errors.hasErrors())
            return editReview(reviewForm, reviewId);

        reviewLogger.info("editing review with id '{}' with params --> rating: {}, message: {}",
                reviewId, reviewForm.getRating(), reviewForm.getMessage());

        Review review = reviewService.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        reviewService.update(reviewForm.getRating(), reviewForm.getMessage(), reviewId);

        return new ModelAndView("redirect:/article/" + review.getArticleId());
    }

    private void populateReviewForm(Review review, ReviewForm reviewForm) {
        reviewForm.setRating(review.getRating());
        reviewForm.setMessage(review.getMessage());
    }
}
