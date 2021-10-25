package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_id_seq")
    @SequenceGenerator(sequenceName = "category_id_seq", name = "category_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    private String description;

    //@ManyToMany(mappedBy = "categories")
    //private List<Article> articles;

    public Category(long id, String description) {
        this.id = id;
        this.description = description;
    }

    /* package */ Category() {
        // Just for Hibernate
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

    /*
    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(description, category.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, id);
    }

    @Override
    public String toString() {
        return description;
    }
}
