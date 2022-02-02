package ar.edu.itba.paw.webapp.dto.get;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.forms.annotations.FieldsEquality;
import ar.edu.itba.paw.webapp.forms.annotations.UserNotExists;
import ar.edu.itba.paw.webapp.forms.annotations.ValidFile;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.Map;

public class UserDTO {

    private String firstName;
    private String lastName;
    private String email;
    private long id;

    private boolean isOwner;

    private Long pendingRequestAmount;
    private Long acceptedRequestAmount;
    private Long declinedRequestAmount;

    private URI url;
    private URI imageUrl;
    private URI locationUrl;

    public static UserDTO fromUser(User user, UriInfo uri) {
        UserDTO toReturn = new UserDTO();
        toReturn.firstName = user.getFirstName();
        toReturn.lastName = user.getLastName();
        toReturn.email = user.getEmail();
        toReturn.id = user.getId();
        toReturn.isOwner = user.getType().getIsOwner();
        toReturn.pendingRequestAmount = user.getPendingRequestAmount();
        toReturn.acceptedRequestAmount = user.getAcceptedRequestAmount();
        toReturn.declinedRequestAmount = user.getDeclinedRequestAmount();
        toReturn.imageUrl = uri.getBaseUriBuilder().path("images").path(String.valueOf(user.getPicture().getId())).build();
        toReturn.locationUrl = uri.getBaseUriBuilder().path("locations").path(String.valueOf(user.getLocation().ordinal())).build();
        toReturn.url = uri.getBaseUriBuilder().path("users").path(String.valueOf(user.getId())).build();
        return toReturn;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public long getPendingRequestAmount() {
        return pendingRequestAmount;
    }

    public void setPendingRequestAmount(long pendingRequestAmount) {
        this.pendingRequestAmount = pendingRequestAmount;
    }

    public long getAcceptedRequestAmount() {
        return acceptedRequestAmount;
    }

    public void setAcceptedRequestAmount(long acceptedRequestAmount) {
        this.acceptedRequestAmount = acceptedRequestAmount;
    }

    public long getDeclinedRequestAmount() {
        return declinedRequestAmount;
    }

    public void setDeclinedRequestAmount(long declinedRequestAmount) {
        this.declinedRequestAmount = declinedRequestAmount;
    }

    public void setPendingRequestAmount(Long pendingRequestAmount) {
        this.pendingRequestAmount = pendingRequestAmount;
    }

    public void setAcceptedRequestAmount(Long acceptedRequestAmount) {
        this.acceptedRequestAmount = acceptedRequestAmount;
    }

    public void setDeclinedRequestAmount(Long declinedRequestAmount) {
        this.declinedRequestAmount = declinedRequestAmount;
    }

    public URI getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(URI imageUrl) {
        this.imageUrl = imageUrl;
    }

    public URI getLocationUrl() {
        return locationUrl;
    }

    public void setLocationUrl(URI locationUrl) {
        this.locationUrl = locationUrl;
    }

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }
}
