package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.models.User;
import javax.validation.*;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriBuilder;
import java.util.*;

public class ApiUtils {

    public static final int CACHE_MAX_AGE = 31536000;

    private ApiUtils(){}

    public static Response generateResponseWithLinks(Response.ResponseBuilder response,
                                                     long currentPage, long maxPage, UriBuilder uri){
        response.link(uri.clone().queryParam("page", 1).build(), "first");
        response.link(uri.clone().queryParam("page", maxPage).build(), "last");

        if(currentPage > 1) {
            response.link(uri.clone().queryParam("page", currentPage - 1).build(), "prev");
        }

        if(currentPage < maxPage) {
            response.link(uri.clone().queryParam("page", currentPage + 1).build(), "next");
        }

        return response.build();
    }

    public static User retrieveUser(SecurityContext context, UserService userService){
        return userService.findByEmail(context.getUserPrincipal().getName()).orElse(null);
    }

    public static <T> T validateBean(T toValidate){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(toValidate);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        return toValidate;
    }
}
