package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriBuilder;

public class PaginationProvider {

    private PaginationProvider(){
    }

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
}
