package ar.edu.itba.paw.models;

public enum OrderOptions {

    PRICE("enum.order.price", "price_per_day"),
    ARTICLE("enum.order.article", "description");

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
