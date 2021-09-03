package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.ArticleNotFoundException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.ArticleService;
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
public class HelloWorldController {
    @Autowired
    UserService userService;

    @Autowired
    ArticleService articleService;

    @RequestMapping("/")
    public ModelAndView helloWorld() {
        final ModelAndView mav = new ModelAndView("index");
        User user = userService.findById(1).orElseThrow(UserNotFoundException::new);
        mav.addObject("currentUser", user);
        return mav;
    }

    @RequestMapping("/marketplace")
    public ModelAndView marketplace() {
        final ModelAndView mav = new ModelAndView("articles");
        List<Article> articles = articleService.list();
        mav.addObject("articles", articles);
        return mav;
    }

    @RequestMapping("/register")
    public ModelAndView register(@RequestParam(value = "email", required = true) String email,
                                 @RequestParam(value = "password", required = true) String password,
                                 @RequestParam(value = "first-name", required = true) String firstName,
                                 @RequestParam(value = "last-name", required = true) String lastName,
                                 @RequestParam(value = "location", required = true) String location,
                                 @RequestParam(value = "type", required = true) int type) {
        final ModelAndView mav = new ModelAndView("index");
        final User user = userService.register(email, password, firstName, lastName, location, type);
        mav.addObject("currentUserEmail", user.getEmail());
        mav.addObject("currentUserFirstName", user.getEmail());
        mav.addObject("currentUserLastName", user.getEmail());
        mav.addObject("currentUserLocation", user.getEmail());
        return mav;
    }

    @RequestMapping("/{userId}")
    public ModelAndView userProfile(@PathVariable("userId") Integer userId) {
        final ModelAndView mav = new ModelAndView("index");
        User user = userService.findById(userId).orElseThrow(UserNotFoundException::new);
        mav.addObject("currentUser", user);
        return mav;
    }

    @RequestMapping("/article/{articleId}")
    public ModelAndView viewArticle(@PathVariable("articleId") Integer articleId) {
        final ModelAndView mav = new ModelAndView("article");
        Article article = articleService.findById(articleId).orElseThrow(ArticleNotFoundException::new);
        mav.addObject("article", article);
        User user = userService.findById(1).orElseThrow(UserNotFoundException::new);
        mav.addObject("owner", user);
        return mav;
    }
}