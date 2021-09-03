package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.ArticleNotFoundException;
import ar.edu.itba.paw.interfaces.ArticleService;
import ar.edu.itba.paw.models.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @RequestMapping("/marketplace")
    public ModelAndView marketplace() {
        final ModelAndView mav = new ModelAndView("articles");
        List<Article> articles = articleService.list();
        mav.addObject("articles", articles);
        return mav;
    }

    @RequestMapping("/article/{articleId}")
    public ModelAndView viewArticle(@PathVariable("articleId") Integer articleId) {
        final ModelAndView mav = new ModelAndView("article");
        Article article = articleService.findById(articleId).orElseThrow(ArticleNotFoundException::new);
        mav.addObject("article", article);
        return mav;
    }
}
