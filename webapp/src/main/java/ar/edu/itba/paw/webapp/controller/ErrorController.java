package ar.edu.itba.paw.webapp.controller;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@Controller
public class ErrorController extends BaseController {
    @Autowired
    LoggedUserAdvice loggedUserAdvice;

    @RequestMapping("/403")
    public ModelAndView forbidden() {
        ModelAndView mv = new ModelAndView("error/403");
        mv.addObject("user", loggedUser());
        return mv;
    }

    @RequestMapping("/400")
    @ExceptionHandler({ TypeMismatchException.class, MissingServletRequestPartException.class, MissingServletRequestParameterException.class, 
                        BindException.class, HttpMessageNotReadableException.class, MethodArgumentNotValidException.class})
    public ModelAndView badRequest() {
        ModelAndView mv = new ModelAndView("error/400");
        mv.addObject("user", loggedUser());
        return mv;
    }

    @RequestMapping("/404")
    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView notFound(Exception e) {
        ModelAndView mv = new ModelAndView("error/404");
        mv.addObject("user", loggedUser());
        return mv;
    }
}
