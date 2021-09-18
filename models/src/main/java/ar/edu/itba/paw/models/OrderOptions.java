package ar.edu.itba.paw.models;

public enum OrderOptions {

    LOWER_ARTICLE("enum.order.lowerArticle", "title ASC"),
    HIGHER_ARTICLE("enum.order.higherArticle", "title DESC"),
    LOWER_PRICE("enum.order.lowerPrice", "price_per_day ASC"),
    HIGHER_PRICE("enum.order.higherPrice", "price_per_day DESC");

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
