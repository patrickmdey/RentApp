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
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MarketplaceController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public ModelAndView marketplace(@Valid @ModelAttribute("searchForm") SearchForm searchForm,
                                    @RequestParam(value = "page", required = false, defaultValue = "1") Long page
    ) {

        final ModelAndView mav = new ModelAndView("marketplace");

        System.out.println("ESTAMOS ACA");

        List<Article> articles = articleService.get(searchForm.getQuery(), searchForm.getCategory(),
                searchForm.getOrderBy(), searchForm.getUser(), searchForm.getLocation(), page);
        System.out.println("LA QUEDAMOS EN EL GET ACA");


        List<Category> categories = categoryService.listCategories();

        mav.addObject("categories", categories);
        mav.addObject("orderOptions", OrderOptions.values());
        mav.addObject("maxPage", articleService.getMaxPage(searchForm.getQuery(),
                searchForm.getCategory(), searchForm.getUser(), searchForm.getLocation()));
        mav.addObject("articles", articles);
        mav.addObject("query", searchForm.getQuery());

        mav.addObject("locations", Arrays.stream(Locations.values())
                .sorted(Comparator.comparing(Locations::getName))
                .collect(Collectors.toList()));
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
