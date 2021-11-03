package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.*;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Category;
import ar.edu.itba.paw.models.RentState;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.ArticleNotFoundException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.forms.CreateArticleForm;
import ar.edu.itba.paw.webapp.forms.EditArticleForm;
import ar.edu.itba.paw.webapp.forms.RentProposalForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/article")
public class ArticleController {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @Autowired
    private RentService rentService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private LoggedUserAdvice userAdvice;

    @Autowired
    private ServletContext servletContext;

    private final Logger articleLogger = LoggerFactory.getLogger(ArticleController.class);

    @RequestMapping(value = "/{articleId}", method = RequestMethod.GET)
    public ModelAndView viewArticle(@ModelAttribute("rentForm") RentProposalForm rentForm,
                                    @PathVariable("articleId") long articleId,
                                    @RequestParam(value = "requestFormHasErrors", required = false, defaultValue = "false") boolean requestFormHasErrors,
                                    @RequestParam(value = "page", required = false, defaultValue = "1") long page) {
        final ModelAndView mav = new ModelAndView("article");
        Article article = articleService.findById(articleId).orElseThrow(ArticleNotFoundException::new);
        User owner = userService.findById(article.getOwner().getId()).orElseThrow(UserNotFoundException::new);

        mav.addObject("article", article);
        mav.addObject("owner", owner);
        mav.addObject("requestFormHasErrors", requestFormHasErrors);
        mav.addObject("reviews", reviewService.getPaged(articleId, page));
        mav.addObject("articleRating", article.getRating());

        mav.addObject("canReview",
                rentService.hasRented(userAdvice.loggedUser(), articleId) &&
                        !reviewService.hasReviewed(userAdvice.loggedUser(), articleId));
        mav.addObject("maxPage", reviewService.getMaxPage(articleId));
        mav.addObject("recommended", articleService.recommendedArticles(articleId));
        return mav;
    }

    @RequestMapping(value = "/{articleId}", method = RequestMethod.POST)
    public ModelAndView createProposal(@Valid @ModelAttribute("rentForm") RentProposalForm rentForm,
                                       BindingResult errors, @PathVariable("articleId") long articleId) {
        if (errors.hasErrors()) {
            return viewArticle(rentForm, articleId, true, 1L);
        }

        articleLogger.info("creating new rent proposal with params --> message: {}, articleId: {}, renterEmail: {}",
                rentForm.getMessage(), articleId, userAdvice.loggedUser().getEmail());

        String webpageUrl = ServletUriComponentsBuilder.fromCurrentRequestUri().scheme("http").replacePath(null).build().toUriString() + servletContext.getContextPath();
        rentService.create(rentForm.getMessage(),
                LocalDate.parse(rentForm.getStartDate(), DATE_FORMAT),
                LocalDate.parse(rentForm.getEndDate(), DATE_FORMAT),
                articleId, userAdvice.loggedUser().getId(), webpageUrl);

        return new ModelAndView("redirect:/feedback");
    }

    @RequestMapping("/create")
    public ModelAndView viewCreateArticleForm(@ModelAttribute("createArticleForm") CreateArticleForm createArticleForm) {
        final ModelAndView mav = new ModelAndView("createArticle");
        List<Category> categories = categoryService.listCategories();
        mav.addObject("categories", categories);
        mav.addObject("isEdit", false);
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
                userAdvice.loggedUser().getId());

        articleLogger.info("creating article with params --> name: {}, description: {}, price: {}, categories: {}",
                article.getTitle(), article.getDescription(), article.getPricePerDay(), article.getCategories());

        return new ModelAndView("redirect:/article/" + Math.toIntExact(article.getId()));
    }

    @RequestMapping(value = "/{articleId}/edit", method = RequestMethod.GET)
    @PreAuthorize("@webSecurity.checkIsArticleOwner(authentication,#articleId)")
    public ModelAndView viewEditArticleForm(@ModelAttribute("createArticleForm") EditArticleForm editArticleForm, @PathVariable("articleId") long articleId) {
        final ModelAndView mav = new ModelAndView("createArticle");

        List<Category> categories = categoryService.listCategories();
        Optional<Article> articleOpt = articleService.findById(articleId);

        if (articleOpt.isPresent()) {
            Article article = articleOpt.get();
            editArticleForm.setName(article.getTitle());
            editArticleForm.setCategories(article.getCategories().stream().map(Category::getId).collect(Collectors.toList()));
            editArticleForm.setDescription(article.getDescription());
            editArticleForm.setPricePerDay(article.getPricePerDay());
        }

        mav.addObject("categories", categories);
        mav.addObject("articleId", articleId);
        mav.addObject("isEdit", true);
        return mav;
    }

    @RequestMapping(value = "/{articleId}/edit", method = RequestMethod.POST)
    @PreAuthorize("@webSecurity.checkIsArticleOwner(authentication,#articleId)")
    public ModelAndView editArticle(@Valid @ModelAttribute("createArticleForm") EditArticleForm createArticleForm,
                                    BindingResult errors, @PathVariable("articleId") long articleId) {
        if (errors.hasErrors()) {
            return viewEditArticleForm(createArticleForm, articleId);
        }

        Article article = articleService.editArticle(
                articleId,
                createArticleForm.getName(),
                createArticleForm.getDescription(),
                createArticleForm.getPricePerDay(),
                createArticleForm.getCategories()).orElseThrow(ArticleNotFoundException::new);

        articleLogger.info("Editing article post with params --> name: {}, description: {}, price: {}, categories: {}",
                article.getTitle(), article.getDescription(), article.getPricePerDay(), article.getCategories());

        return new ModelAndView("redirect:/article/" + article.getId());
    }
}
