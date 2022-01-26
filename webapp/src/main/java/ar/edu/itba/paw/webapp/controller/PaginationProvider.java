package ar.edu.itba.paw.webapp.controller;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

public class PaginationProvider {

    private PaginationProvider(){
    }

    public static Response generateResponseWithLinks(Response.ResponseBuilder response,
                                                     long currentPage, long maxPage, UriBuilder uri){
        response.link(uri.clone().queryParam("page", 1).build(), "first");
        response.link(uri.clone().queryParam("page", maxPage).build(), "last");

        if(currentPage > 1){
            response.link(uri.clone().queryParam("page", currentPage - 1).build(), "prev");
        }

        if(currentPage < maxPage){
            response.link(uri.clone().queryParam("page", currentPage + 1).build(), "next");
        }

        return response.build();
    }
}
