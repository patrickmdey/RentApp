package ar.edu.itba.paw.webapp.exceptionMapper;

import ar.edu.itba.paw.models.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Component
@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    @Autowired
    private MessageSource messageSource;

    @Override
    public Response toResponse(NotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN_TYPE).
                entity(messageSource.getMessage(e.getMessage(), null, LocaleContextHolder.getLocale())).build();
    }
}