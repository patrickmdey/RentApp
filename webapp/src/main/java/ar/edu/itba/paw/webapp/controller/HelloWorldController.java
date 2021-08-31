package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {
    @Autowired
    UserService userService;

    @RequestMapping("/")
    public ModelAndView helloWorld() {
        final ModelAndView mav = new ModelAndView("index");
        User user = userService.findById(1).orElseThrow(UserNotFoundException::new);
        mav.addObject("currentUser", user);
        return mav;
    }

    @RequestMapping("/create")
    public ModelAndView register(@RequestParam(value = "email", required = true) String email,
                                 @RequestParam(value = "password", required = true) String password) {
        final ModelAndView mav = new ModelAndView("index");
        final User user = userService.register(email, password);
        mav.addObject("currentUser", user.getEmail());
        return mav;
    }

    @RequestMapping("/{userId}")
    public ModelAndView userProfile(@PathVariable("userId") long userId) {
        final ModelAndView mav = new ModelAndView("index");
        User user = userService.findById(userId).orElseThrow(UserNotFoundException::new);
        mav.addObject("currentUser", user);
        return mav;
    }


}
