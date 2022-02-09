package ar.edu.itba.paw.webapp.exceptionMapper;

import org.glassfish.jersey.server.ContainerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.Locale;

@Component
@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

        @Autowired
        private MessageSource messageSource;

        @Inject
        private javax.inject.Provider<ContainerRequest> requestProvider;

        @Override
        public Response toResponse(IllegalArgumentException e) {
                List<Locale> languages = requestProvider.get().getAcceptableLanguages();
                Locale lang = languages.isEmpty() ? Locale.ENGLISH : languages.get(0);
                return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN_TYPE).
                        entity(messageSource.getMessage(e.getMessage(), null, lang)).build();
        }
}
