package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.ArticleNotFoundException;
import ar.edu.itba.paw.exceptions.CannotCreateArticleException;
import ar.edu.itba.paw.exceptions.ReviewNotFoundException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.forms.CreateArticleForm;
import ar.edu.itba.paw.webapp.forms.EditArticleForm;
import ar.edu.itba.paw.webapp.forms.RentProposalForm;
import ar.edu.itba.paw.webapp.forms.ReviewForm;
import ar.edu.itba.paw.webapp.forms.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.List;

@Controller
public class ArticleController extends BaseController {
    @Autowired
    ArticleService articleService;

    @Autowired
    UserService userService;

    @Autowired
    RentService rentService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    EmailService emailService;

    @Autowired
    ReviewService reviewService;

    @RequestMapping("/")
    public ModelAndView marketplace(@ModelAttribute("searchForm") SearchForm searchForm,
                                    @RequestParam(value = "page", required = false, defaultValue = "1") Long page) {
        final ModelAndView mav = new ModelAndView("marketplace");
        List<Article> articles = articleService.get(searchForm.getQuery(), searchForm.getCategory(),
                searchForm.getOrderBy(), searchForm.getUser(), searchForm.getLocation(), page);
        mav.addObject("articles", articles);
        mav.addObject("query", searchForm.getQuery());
        List<Category> categories = categoryService.listCategories();
        mav.addObject("categories", categories);
        mav.addObject("orderOptions", OrderOptions.values());
        mav.addObject("maxPage", articleService.getMaxPage(searchForm.getQuery(),
                searchForm.getCategory(), searchForm.getUser(), searchForm.getLocation()));

        mav.addObject("locations", Arrays.stream(Locations.values())
                .sorted(Comparator.comparing(Locations::getName))
                .collect(Collectors.toList()));

        mav.addObject("locationsEnum", Locations.values());

        mav.addObject("category", categoryService.findById(searchForm.getCategory()));

        mav.addObject("userFilter", userService.findById(searchForm.getUser()).orElse(null));
        return mav;
    }

    @RequestMapping(value = "/article/{articleId}", method = RequestMethod.GET)
    public ModelAndView viewArticle(@ModelAttribute("rentForm") RentProposalForm rentForm,
                                    @PathVariable("articleId") Long articleId,
                                    @RequestParam(value = "requestFormHasErrors", required = false) Boolean requestFormHasErrors,
                                    @RequestParam(value = "page", required = false, defaultValue = "1") Long page) {
        final ModelAndView mav = new ModelAndView("article");
        Article article = articleService.findById(articleId).orElseThrow(ArticleNotFoundException::new);
        mav.addObject("article", article);
        User owner = userService.findById(article.getIdOwner()).orElseThrow(UserNotFoundException::new);
        article.setLocation(Locations.values()[Math.toIntExact(owner.getLocation())]);

        mav.addObject("owner", owner);
        mav.addObject("requestFormHasErrors", requestFormHasErrors);
        mav.addObject("reviews", reviewService.getPaged(articleId, page));
        mav.addObject("articleRating", reviewService.articleRating(articleId));

        mav.addObject("hasRented", rentService.hasRented(loggedUser(), articleId));

        mav.addObject("maxPage", reviewService.getMaxPage(articleId));

        mav.addObject("recommended", articleService.recommendedArticles(articleId));
        return mav;
    }

    @RequestMapping(value = "/article/{articleId}", method = RequestMethod.POST)
    public ModelAndView createProposal(@Valid @ModelAttribute("rentForm") RentProposalForm rentForm, BindingResult errors, @PathVariable("articleId") Long articleId) throws ParseException {

        if (errors.hasErrors()) {
            return viewArticle(rentForm, articleId, true, 1L);
        }

        rentService.create(rentForm.getMessage(), RentState.PENDING.ordinal(), new SimpleDateFormat("yyyy-MM-dd").parse(rentForm.getStartDate()),
                new SimpleDateFormat("yyyy-MM-dd").parse(rentForm.getEndDate()),
                articleId, loggedUser().getFirstName(), loggedUser().getEmail(), loggedUser().getId()).orElseThrow(CannotCreateArticleException::new);

        return new ModelAndView("feedback"); //TODO: redirect aca
    }

    @RequestMapping("/create-article")
    public ModelAndView viewCreateArticleForm(@ModelAttribute("createArticleForm") CreateArticleForm createArticleForm) {
        final ModelAndView mav = new ModelAndView("createArticle");
        List<Category> categories = categoryService.listCategories();
        mav.addObject("categories", categories);
        return mav;
    }

    @RequestMapping(value = "/create-article", method = RequestMethod.POST)
    public ModelAndView createArticle(@Valid @ModelAttribute("createArticleForm") CreateArticleForm createArticleForm,
                                      BindingResult errors) {


        if (errors.hasErrors()) {
            return viewCreateArticleForm(createArticleForm);
        }

        Article article = articleService.createArticle(
                createArticleForm.getName(),
                createArticleForm.getDescription(),
                createArticleForm.getPricePerDay(),
                createArticleForm.getCategories(),
                createArticleForm.getFiles(),
                loggedUser().getId()).orElseThrow(CannotCreateArticleException::new); //TODO: Harcodeado el OwnerId

        return new ModelAndView("redirect:/article/" + Math.toIntExact(article.getId()));
    }

    @RequestMapping(value = "/article/{articleId}/edit", method = RequestMethod.GET)
    @PreAuthorize("@webSecurity.checkIsArticleOwner(authentication,#articleId)")
    public ModelAndView viewEditArticleForm(@ModelAttribute("createArticleForm") EditArticleForm editArticleForm, @PathVariable("articleId") Long articleId) {
        final ModelAndView mav = new ModelAndView("createArticle");

        List<Category> categories = categoryService.listCategories();

        Optional<Article> articleOpt = articleService.findById(articleId.intValue());

        if (articleOpt.isPresent()) {
            Article article = articleOpt.get();
            editArticleForm.setName(article.getTitle());
            editArticleForm.setCategories(article.getCategories().stream().map(Category::getId).collect(Collectors.toList()));
            editArticleForm.setDescription(article.getDescription());
            editArticleForm.setPricePerDay(article.getPricePerDay());
        }

        mav.addObject("categories", categories);
        mav.addObject("articleId", articleId);
        return mav;
    }

    @RequestMapping(value = "/article/{articleId}/review/create")
    public ModelAndView publishReview(@ModelAttribute("reviewForm") ReviewForm reviewForm,
                                      BindingResult errors, @PathVariable("articleId") Long articleId) {
        ModelAndView mav = new ModelAndView("review/create");
        mav.addObject("rating", new Integer[]{1, 2, 3, 4, 5});
        mav.addObject("article", articleService.findById(articleId).orElseThrow(ArticleNotFoundException::new));
        return mav;
    }

    @RequestMapping(value = "/article/{articleId}/review/create", method = RequestMethod.POST)
    @PreAuthorize("@webSecurity.checkCanReview(authentication,#articleId)")
    public ModelAndView createReview(@Valid @ModelAttribute("reviewForm") ReviewForm reviewForm,
                                     BindingResult errors, @PathVariable("articleId") Long articleId) {
        if (errors.hasErrors()) {
            return publishReview(reviewForm, errors, articleId);
        }
        reviewService.create(reviewForm.getRating(), reviewForm.getMessage(), articleId, loggedUser().getId());
        return new ModelAndView("redirect:/article/" + articleId);
    }

    @RequestMapping(value = "/article/{articleId}/review/{reviewId}/edit", method = RequestMethod.GET)
    @PreAuthorize("@webSecurity.checkIsReviewOwner(authentication,#reviewId)")
    public ModelAndView editReview(@ModelAttribute("reviewForm") ReviewForm reviewForm,
                                   @PathVariable("articleId") Long articleId,
                                   @PathVariable("reviewId") Long reviewId) {
        final ModelAndView mav = new ModelAndView("review/edit");
        mav.addObject("rating", new Integer[]{1, 2, 3, 4, 5});
        mav.addObject("article", articleService.findById(articleId).orElseThrow(ArticleNotFoundException::new));
        populateReviewForm(reviewId, reviewForm);
        return mav;
    }

    @RequestMapping(value = "/article/{articleId}/review/{reviewId}/edit", method = RequestMethod.POST)
    @PreAuthorize("@webSecurity.checkIsReviewOwner(authentication,#reviewId)")
    public ModelAndView updateReview(@Valid @ModelAttribute("reviewForm") ReviewForm reviewForm,
                                     BindingResult errors, @PathVariable("articleId") Long articleId,
                                     @PathVariable("reviewId") Long reviewId) {

        if (errors.hasErrors())
            return editReview(reviewForm, articleId, reviewId);

        reviewService.update(reviewForm.getRating(), reviewForm.getMessage(), reviewId);

        return new ModelAndView("redirect:/article/" + articleId);
    }

    private void populateReviewForm(long reviewId, ReviewForm reviewForm) {
        Review review = reviewService.findById(reviewId).orElseThrow(ReviewNotFoundException::new);

        reviewForm.setRating(review.getRating());
        reviewForm.setMessage(review.getMessage());
    }

    @RequestMapping(value = "/article/{articleId}/edit", method = RequestMethod.POST)
    @PreAuthorize("@webSecurity.checkIsArticleOwner(authentication,#articleId)")
    public ModelAndView editArticle(@Valid @ModelAttribute("createArticleForm") EditArticleForm createArticleForm,
                                    BindingResult errors, @PathVariable("articleId") Long articleId) {

        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(e -> System.out.println(e.getDefaultMessage()));
            return viewEditArticleForm(createArticleForm, articleId);
        }

        Article article = articleService.editArticle(
                articleId,
                createArticleForm.getName(),
                createArticleForm.getDescription(),
                createArticleForm.getPricePerDay(),
                createArticleForm.getCategories()).orElseThrow(CannotCreateArticleException::new);

        return new ModelAndView("redirect:/article/" + article.getId());
    }
}
