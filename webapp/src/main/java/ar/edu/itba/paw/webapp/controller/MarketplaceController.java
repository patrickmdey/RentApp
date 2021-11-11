package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.ArticleService;
import ar.edu.itba.paw.interfaces.service.CategoryService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Category;
import ar.edu.itba.paw.models.Locations;
import ar.edu.itba.paw.models.OrderOptions;
import ar.edu.itba.paw.webapp.forms.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.util.List;

@Controller
public class MarketplaceController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public ModelAndView landingPage() {
        ModelAndView mav = new ModelAndView("landing");
        List<Article> topRatingArticles = articleService.get(null, null, (long) OrderOptions.HIGHER_RATING.ordinal(), null, null, null, null, 1);
        List<Article> topRentedArticles = articleService.get(null, null, (long) OrderOptions.HIGHER_TIMES_RENTED.ordinal(), null, null, null, null, 1);
        mav.addObject("topRatingArticles", topRatingArticles.subList(0, Math.min(4, topRatingArticles.size())));
        mav.addObject("topRentedArticles", topRentedArticles.subList(0, Math.min(4, topRentedArticles.size())));
        mav.addObject("categories", categoryService.listCategories());
        return mav;
    }

    @RequestMapping("/marketplace")
    public ModelAndView marketplace(@Valid @ModelAttribute("searchForm") SearchForm searchForm,
                                    @RequestParam(value = "page", required = false, defaultValue = "1") long page) {

        final ModelAndView mav = new ModelAndView("marketplace");

        List<Article> articles = articleService.get(searchForm.getQuery(), searchForm.getCategory(),
                searchForm.getOrderBy(), searchForm.getUser(), searchForm.getLocation(),
                searchForm.getInitPrice(), searchForm.getEndPrice(), page);

        List<Category> categories = categoryService.listCategories();

        mav.addObject("categories", categories);
        mav.addObject("orderOptions", OrderOptions.values());
        mav.addObject("maxPage", articleService.getMaxPage(searchForm.getQuery(),
                searchForm.getCategory(), searchForm.getUser(), searchForm.getLocation(), searchForm.getInitPrice(), searchForm.getEndPrice()));
        mav.addObject("articles", articles);
        mav.addObject("query", searchForm.getQuery());

        mav.addObject("locations", articleService.getUsedLocations());
        mav.addObject("locationsEnum", Locations.values());
        mav.addObject("category", categoryService.findById(searchForm.getCategory()).orElse(null));
        mav.addObject("userFilter", userService.findById(searchForm.getUser()).orElse(null));
        return mav;
    }

    @RequestMapping("/feedback")
    public ModelAndView viewFeedback() {
        return new ModelAndView("feedback");
    }
}
