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
@RequestMapping("/article")
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

    @RequestMapping(value = "/{articleId}", method = RequestMethod.GET)
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

        mav.addObject("canReview", rentService.hasRented(loggedUser(), articleId) && !reviewService.hasReviewed(loggedUser(), articleId));

        mav.addObject("maxPage", reviewService.getMaxPage(articleId));

        mav.addObject("recommended", articleService.recommendedArticles(articleId));
        return mav;
    }

    @RequestMapping(value = "/{articleId}", method = RequestMethod.POST)
    public ModelAndView createProposal(@Valid @ModelAttribute("rentForm") RentProposalForm rentForm, BindingResult errors, @PathVariable("articleId") Long articleId) throws ParseException {
        if (errors.hasErrors()) {
            return viewArticle(rentForm, articleId, true, 1L);
        }

        rentService.create(rentForm.getMessage(), RentState.PENDING.ordinal(), new SimpleDateFormat("yyyy-MM-dd").parse(rentForm.getStartDate()),
                new SimpleDateFormat("yyyy-MM-dd").parse(rentForm.getEndDate()),
                articleId, loggedUser().getFirstName(), loggedUser().getEmail(), loggedUser().getId()).orElseThrow(CannotCreateArticleException::new);

        return new ModelAndView("redirect:/feedback");
    }

    @RequestMapping("/create")
    public ModelAndView viewCreateArticleForm(@ModelAttribute("createArticleForm") CreateArticleForm createArticleForm) {
        final ModelAndView mav = new ModelAndView("createArticle");
        List<Category> categories = categoryService.listCategories();
        mav.addObject("categories", categories);
        return mav;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
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

    @RequestMapping(value = "/{articleId}/edit", method = RequestMethod.GET)
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

    @RequestMapping(value = "/{articleId}/edit", method = RequestMethod.POST)
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
