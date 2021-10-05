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
    /*
    @RequestMapping("/400")
    @ExceptionHandler({TypeMismatchException.class, MissingServletRequestPartException.class, MissingServletRequestParameterException.class,
            BindException.class, HttpMessageNotReadableException.class, MethodArgumentNotValidException.class})
    public ModelAndView badRequest() {
        ModelAndView mv = new ModelAndView("error/400");
        mv.addObject("user", userAdvice.loggedUser());
        return mv;
    }

    @RequestMapping("/404")
    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView notFound(Exception e) {
        ModelAndView mv = new ModelAndView("error/404");
        mv.addObject("user", userAdvice.loggedUser());
        return mv;
    }
     */
}
