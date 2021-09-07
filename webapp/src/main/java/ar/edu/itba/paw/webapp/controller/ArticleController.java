package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.ArticleNotFoundException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.ArticleService;
import ar.edu.itba.paw.interfaces.RentService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @Autowired
    UserService userService;
    
    @Autowired
    RentService rentService;

    @RequestMapping("/marketplace")
    public ModelAndView marketplace(@RequestParam(value = "name", required = false) String name) {
        final ModelAndView mav = new ModelAndView("articles");
        List<Article> articles = articleService.get(name);
        System.out.println(articles.isEmpty());
        articles.forEach(article -> System.out.println(article.getTitle()));
        mav.addObject("articles", articles);
        return mav;
    }

    @RequestMapping("/article/{articleId}")
    public ModelAndView viewArticle(@PathVariable("articleId") Integer articleId) {
        final ModelAndView mav = new ModelAndView("article");
        Article article = articleService.findById(articleId).orElseThrow(ArticleNotFoundException::new);
        mav.addObject("article", article);
        User owner = userService.findById(article.getIdOwner()).orElseThrow(UserNotFoundException::new);
        mav.addObject("owner", owner);
        return mav;
    }
}
