package ar.edu.itba.paw.webapp.controller;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public class PaginationProvider {

    private PaginationProvider(){
    }

    public static Response generateResponseWithLinks(Response.ResponseBuilder builder,
                                                     long currentPage, long maxPage, UriInfo uriInfo){
        builder.link(uriInfo.getAbsolutePathBuilder().queryParam("page", 1).build(), "first");
        builder.link(uriInfo.getAbsolutePathBuilder().queryParam("page", maxPage).build(), "last");

        if(currentPage > 1){
            builder.link(uriInfo.getAbsolutePathBuilder().queryParam("page", currentPage - 1).build(), "prev");
        }

        if(currentPage < maxPage){
            builder.link(uriInfo.getAbsolutePathBuilder().queryParam("page", currentPage + 1).build(), "next");
        }

        return builder.build();
    }
}
