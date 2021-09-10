package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;

    @RequestMapping("/register")
    public ModelAndView register(@RequestParam(value = "email", required = true) String email,
                                 @RequestParam(value = "password", required = true) String password,
                                 @RequestParam(value = "first-name", required = true) String firstName,
                                 @RequestParam(value = "last-name", required = true) String lastName,
                                 @RequestParam(value = "location", required = true) String location,
                                 @RequestParam(value = "type", required = true) int type) {
        final ModelAndView mav = new ModelAndView("index");
        final Optional<User> user = userService.register(email, password, firstName, lastName, location, type);
        if (user.isPresent()) {
            mav.addObject("currentUserEmail", user.get().getEmail());
            mav.addObject("currentUserFirstName", user.get().getFirstName());
            mav.addObject("currentUserLastName", user.get().getLastName());
            mav.addObject("currentUserLocation", user.get().getLocation());
        } else
            throw new UserNotFoundException();
        return mav;
    }

//    @RequestMapping("/")
//    public ModelAndView helloWorld() {
//        final ModelAndView mav = new ModelAndView("marketplace");
//        User user = userService.findById(1).orElseThrow(UserNotFoundException::new);
//        mav.addObject("currentUser", user);
//        return mav;
//    }

    @RequestMapping("/{userId}")
    public ModelAndView userProfile(@PathVariable("userId") long userId) {
        final ModelAndView mav = new ModelAndView("index");
        User user = userService.findById(userId).orElseThrow(UserNotFoundException::new);
        mav.addObject("currentUser", user);
        return mav;
    }
}
