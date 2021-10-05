package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.exceptions.*;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.BindException;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @Autowired
    LoggedUserAdvice loggedUserAdvice;

    @ExceptionHandler({ArticleNotFoundException.class, CategoryNotFoundException.class,
            RentProposalNotFoundException.class, ReviewNotFoundException.class,
            UserNotFoundException.class})
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView notFound(Exception exception) {
        ModelAndView mav = new ModelAndView("error/500");
        mav.addObject("message", exception.getMessage());
        mav.addObject("user", loggedUserAdvice.loggedUser());
        return mav;
    }

    @ExceptionHandler({CannotCreateArticleException.class, CannotCreateProposalException.class,
            UnableToSendEmailException.class})
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView databaseError(Exception exception) {
        ModelAndView mav = new ModelAndView("error/500");
        mav.addObject("message", exception.getMessage());
        return mav;
    }


    //    @RequestMapping("/400")
    @ExceptionHandler({TypeMismatchException.class, MissingServletRequestPartException.class,
            MissingServletRequestParameterException.class, BindException.class,
            HttpMessageNotReadableException.class, MethodArgumentNotValidException.class})
    public ModelAndView badRequest() {
        ModelAndView mav = new ModelAndView("error/400");
        mav.addObject("user", loggedUserAdvice.loggedUser());
        return mav;
    }

    //    @RequestMapping("/404")
    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView notFoundE(Exception e) {
        ModelAndView mav = new ModelAndView("error/404");
        mav.addObject("user", loggedUserAdvice.loggedUser());
        return mav;
    }

    /*
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultError() {
        return new ModelAndView("error/404");
    }
     */
}

