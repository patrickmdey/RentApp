package ar.edu.itba.paw.webapp.forms;

public class SearchForm {
    private String query;
    
    private Long category;

    private Long orderBy;

    private Long user;

    private Long location;

    private Float initPrice;

    private Float endPrice;

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

    public Long getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Long orderBy) {
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

    public Float getInitPrice() {
        return initPrice;
    }

    public void setInitPrice(Float initPrice) {
        this.initPrice = initPrice;
    }

    public Float getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(Float endPrice) {
        this.endPrice = endPrice;
    }
}
