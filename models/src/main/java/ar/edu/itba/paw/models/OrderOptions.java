package ar.edu.itba.paw.models;

public enum OrderOptions {

    LOWER_ARTICLE("enum.order.lowerArticle", "ASC", "lower(title)", "LOWER(title)"),
    HIGHER_ARTICLE("enum.order.higherArticle", "DESC", "lower(title)", "LOWER(title)"),
    LOWER_PRICE("enum.order.lowerPrice", "ASC", "pricePerDay", "price_per_day"),
    HIGHER_PRICE("enum.order.higherPrice", "DESC", "pricePerDay", "price_per_day");


    private final String description;
    private final String order;
    private final String jpaColumn;
    private final String nativeColumn;


    OrderOptions(String description, String order, String jpaColumn, String nativeColumn){
        this.description = description;
        this.order = order;
        this.jpaColumn = jpaColumn;
        this.nativeColumn = nativeColumn;
    }


    @Override
    public String toString() {
        return description;
    }

    public String getDescription() {
        return description;
    }

    public String getJpaColumn() {
        return jpaColumn;
    }

    public String getNativeColumn() {
        return nativeColumn;
    }

    public String getOrder() {
        return order;
    }
}