package ar.edu.itba.paw.webapp.dto.get;

import ar.edu.itba.paw.models.OrderOptions;
import org.springframework.context.MessageSource;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Locale;

public class OrderOptionDTO {

    private URI url;
    private String description;

    public static OrderOptionDTO fromOrderOption(OrderOptions option, UriInfo uri, MessageSource messageSource, Locale locale) {
        OrderOptionDTO toReturn = new OrderOptionDTO();
        toReturn.url = uri.getBaseUriBuilder().path("orderOptions").path(String.valueOf(option.ordinal())).build();
        toReturn.description = messageSource.getMessage(option.getDescription(), null, locale);
        return toReturn;
    }

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
