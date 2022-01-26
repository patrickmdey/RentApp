package ar.edu.itba.paw.webapp.exceptionMapper;

import ar.edu.itba.paw.models.exceptions.InternalErrorException;
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
public class InternalErrorExceptionMapper implements ExceptionMapper<InternalErrorException> {

    @Autowired
    private MessageSource messageSource;

    @Override
    public Response toResponse(InternalErrorException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN_TYPE).
                entity(messageSource.getMessage(e.getMessage(), null, LocaleContextHolder.getLocale())).build();
    }
}

