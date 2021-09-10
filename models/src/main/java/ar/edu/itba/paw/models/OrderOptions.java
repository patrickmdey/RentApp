package ar.edu.itba.paw.models;

public enum OrderOptions {

    LOWER_PRICE("enum.order.lower_price", "price_per_day ASC"),
    HIGHER_PRICE("enum.order.higher_price", "price_per_day DESC"),
    LOWER_ARTICLE("enum.order.lower_article", "title ASC"),
    HIGHER_ARTICLE("enum.order.higher_article", "title DESC");

    public String getDescription() {
        return description;
    }

    private final String description;
    private final String column;

    OrderOptions(String description, String column){
        this.description = description;
        this.column = column;
    }

    public String getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return description;
    }
}
