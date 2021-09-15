package ar.edu.itba.paw.models;

import java.util.Objects;

public class Category {

    private String description;
    private long id;

    public Category(long id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id == category.id && Objects.equals(description, category.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, id);
    }
}
