package ar.edu.itba.paw.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {
    @Autowired
    private LoggedUserAdvice userAdvice;

    @RequestMapping("/403")
    public ModelAndView forbidden() {
        ModelAndView mav = new ModelAndView("error/403");
        mav.addObject("user", userAdvice.loggedUser());
        return mav;
    }

}
