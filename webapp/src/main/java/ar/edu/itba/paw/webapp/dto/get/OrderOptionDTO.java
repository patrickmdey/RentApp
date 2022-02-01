package ar.edu.itba.paw.webapp.dto.get;

import ar.edu.itba.paw.models.OrderOptions;
import org.springframework.context.MessageSource;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Locale;

public class OrderOptionDTO {

    private URI url;
    private String description;
    private String order;

    //TODO: que se hace con estas props (del modelo) ?
    //      private final String jpaColumn;
    //      private final String nativeColumn;

    public static OrderOptionDTO fromOrderOption(OrderOptions option, UriInfo uri, MessageSource messageSource, Locale locale) {
        OrderOptionDTO toReturn = new OrderOptionDTO();
        toReturn.url = uri.getBaseUriBuilder().path("orderOptions").path(String.valueOf(option.ordinal())).build();
        toReturn.description = messageSource.getMessage(option.getDescription(), null, locale);
        toReturn.order = option.getOrder();
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

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}