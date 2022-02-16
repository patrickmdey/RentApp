package ar.edu.itba.paw.webapp.utils;

import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.models.User;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriBuilder;
import java.util.List;
import java.util.Locale;
import java.util.Set;

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

    public static <T> T validateBean(T toValidate, ValidatorFactory validatorFactory){
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(toValidate);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        return toValidate;
    }

    public static Locale resolveLocale(List<Locale> received){
        Locale toReturn = Locale.ENGLISH;
        for (Locale locale : received) {
            if (locale.toLanguageTag().contains("es"))
                toReturn = Locale.forLanguageTag("es");
        }
        return toReturn;
    }
}