package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.ArticleNotFoundException;
import ar.edu.itba.paw.exceptions.CannotCreateArticleException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.forms.CreateArticleForm;
import ar.edu.itba.paw.webapp.forms.RentProposalForm;
import ar.edu.itba.paw.webapp.forms.ReviewForm;
import ar.edu.itba.paw.webapp.forms.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.*;
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

    @ModelAttribute(value = "locationsEnum")
    public Locations[] locationsEnum() {
        return Locations.values();
    }

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

        mav.addObject("category", categoryService.findById(searchForm.getCategory()));

        mav.addObject("userFilter", userService.findById(searchForm.getUser()).orElse(null));
        return mav;
    }

    @RequestMapping(value = "/article/{articleId}", method = RequestMethod.GET)
    public ModelAndView viewArticle(@ModelAttribute("rentForm") RentProposalForm rentForm,
                                    @PathVariable("articleId") Integer articleId,
                                    @RequestParam(value = "requestFormHasErrors", required = false) Boolean requestFormHasErrors) {
        final ModelAndView mav = new ModelAndView("article");
        Article article = articleService.findById(articleId).orElseThrow(ArticleNotFoundException::new);
        mav.addObject("article", article);
        User owner = userService.findById(article.getIdOwner()).orElseThrow(UserNotFoundException::new);
        article.setLocation(Locations.values()[Math.toIntExact(owner.getLocation())].getName());

        mav.addObject("owner", owner);
        mav.addObject("requestFormHasErrors", requestFormHasErrors);
        mav.addObject("reviews", reviewService.getAllArticleReviews(articleId));

        mav.addObject("recommended", articleService.recommendedArticles(articleId));
        return mav;
    }

    @RequestMapping(value = "/article/{articleId}", method = RequestMethod.POST)
    public ModelAndView createProposal(@Valid @ModelAttribute("rentForm") RentProposalForm rentForm, BindingResult errors, @PathVariable("articleId") Integer articleId) throws ParseException {

        if (errors.hasErrors()) {
            return viewArticle(rentForm, articleId, true);
        }

        rentService.create(rentForm.getMessage(), false, new SimpleDateFormat("yyyy-MM-dd").parse(rentForm.getStartDate()),
                new SimpleDateFormat("yyyy-MM-dd").parse(rentForm.getEndDate()),
                articleId, loggedUser().getFirstName(), loggedUser().getEmail(), loggedUser().getId()).orElseThrow(CannotCreateArticleException::new);

        return new ModelAndView("feedback");
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
                                      BindingResult errors, @ModelAttribute("rentForm") RentProposalForm rentProposalForm) {


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

        return viewArticle(rentProposalForm, Math.toIntExact(article.getId()), false);
    }

    @RequestMapping(value = "/article/{articleId}/edit", method = RequestMethod.GET)
    @PreAuthorize("@webSecurity.checkIsArticleOwner(authentication,#articleId)")
    public ModelAndView viewEditArticleForm(@ModelAttribute("createArticleForm") CreateArticleForm editArticleForm, BindingResult errors, @PathVariable("articleId") Long articleId) {
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

    @RequestMapping(value = "/article/{articleId}/review")
    public ModelAndView publishReview(@ModelAttribute("reviewForm") ReviewForm reviewForm,
                                      BindingResult errors, @PathVariable("articleId") Long articleId,
                                      @ModelAttribute("rentForm") RentProposalForm rentProposalForm) {
        ModelAndView mav = new ModelAndView("createReview");
        mav.addObject("rating", new Integer[]{1, 2, 3, 4, 5});
        return mav;
    }

    @RequestMapping(value = "/article/{articleId}/review", method = RequestMethod.POST)
    public ModelAndView createReview(@Valid @ModelAttribute("reviewForm") ReviewForm reviewForm,
                                     BindingResult errors, @PathVariable("articleId") Long articleId,
                                     @ModelAttribute("rentForm") RentProposalForm rentProposalForm) {
        if (errors.hasErrors()) {
            return publishReview(reviewForm, errors, articleId, rentProposalForm);
        }
        reviewService.create(reviewForm.getRating(), reviewForm.getMessage(), articleId, loggedUser().getId());
        return viewArticle(rentProposalForm, articleId.intValue(), false);
    }


    @RequestMapping(value = "/article/{articleId}/edit", method = RequestMethod.POST)
    @PreAuthorize("@webSecurity.checkIsArticleOwner(authentication,#articleId)")
    public ModelAndView editArticle(@Valid @ModelAttribute("createArticleForm") CreateArticleForm createArticleForm,
                                    BindingResult errors, @PathVariable("articleId") Long articleId, @ModelAttribute("rentForm") RentProposalForm rentProposalForm) {

        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(e -> System.out.println(e.getDefaultMessage()));
            return viewCreateArticleForm(createArticleForm);
        }

        Article article = articleService.editArticle(
                articleId,
                createArticleForm.getName(),
                createArticleForm.getDescription(),
                createArticleForm.getPricePerDay(),
                createArticleForm.getCategories()).orElseThrow(CannotCreateArticleException::new);

        return viewArticle(rentProposalForm, Math.toIntExact(article.getId()), false);
    }
}
