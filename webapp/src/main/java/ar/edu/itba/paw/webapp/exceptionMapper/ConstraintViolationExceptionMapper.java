package ar.edu.itba.paw.webapp.exceptionMapper;

import org.glassfish.jersey.server.ContainerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Autowired
    private MessageSource messageSource;

    @Inject
    private javax.inject.Provider<ContainerRequest> requestProvider;

    @Override
    public Response toResponse(ConstraintViolationException e) {
        List<Locale> languages = requestProvider.get().getAcceptableLanguages();
        Locale lang = languages.isEmpty() ? Locale.ENGLISH : languages.get(0);

        Map<String, StringBuilder> violations = new HashMap<>();
        for(ConstraintViolation<?> v : e.getConstraintViolations()) {
            String message = messageSource.getMessage(v.getMessage(), null, lang);
            for (Map.Entry<String, Object> entry: v.getConstraintDescriptor().getAttributes().entrySet()) {
                message = message.replace('{' + entry.getKey() + '}', entry.getValue().toString());
            }
            String key = v.getPropertyPath().toString();
            if (key.length() == 0) {
                key = v.getConstraintDescriptor().getMessageTemplate().split("\\.")[0];
            }
            violations.putIfAbsent(key, new StringBuilder());
            violations.get(key).append(message).append(".");
        }
        StringBuilder response = new StringBuilder();
        violations.forEach((key, value) -> response.append(key).append(": ").append(value).append("\n"));

        response.deleteCharAt(response.length() - 1);

        return Response.status(Response.Status.BAD_REQUEST).entity(response.toString())
                .type(MediaType.APPLICATION_JSON).build();
    }
}