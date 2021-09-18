package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.ArticleNotFoundException;
import ar.edu.itba.paw.exceptions.CannotCreateArticleException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.forms.CreateArticleForm;
import ar.edu.itba.paw.webapp.forms.RentProposalForm;
import ar.edu.itba.paw.webapp.forms.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
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
        return mav;
    }

    @RequestMapping(value = "/article/{articleId}", method = RequestMethod.POST)
    public ModelAndView createProposal(@Valid @ModelAttribute("rentForm") RentProposalForm rentForm, BindingResult errors, @PathVariable("articleId") Integer articleId) throws ParseException {

        if (errors.hasErrors()) {
            System.out.println("err");
            errors.getAllErrors().forEach(System.out::println);
            return viewArticle(rentForm, articleId, true);
        }

        rentService.create(rentForm.getMessage(), false, new SimpleDateFormat("yyyy-MM-dd").parse(rentForm.getStartDate()),
                new SimpleDateFormat("yyyy-MM-dd").parse(rentForm.getEndDate()),
                articleId, rentForm.getName(), rentForm.getEmail(), 1);

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
}
