package ar.edu.itba.paw.webapp.forms;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class SearchForm {
    private String query;

    @Min(1)
    @Max(8)
    private Long category;

    private String orderBy;

    private Long user;

    @Min(0)
    @Max(47)
    private Long location;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Long getLocation() {
        return location;
    }

    public void setLocation(Long location) {
        this.location = location;
    }
}
