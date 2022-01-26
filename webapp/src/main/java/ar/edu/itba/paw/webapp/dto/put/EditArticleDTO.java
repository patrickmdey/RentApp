package ar.edu.itba.paw.webapp.dto.put;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class EditArticleDTO {

    @NotEmpty
    @Size(min = 5, max = 50)
    private String title;

    @NotEmpty
    @Size(min = 10, max = 700)
    private String description;

    @NotNull
    @Min(1)
    private Float pricePerDay;

    @NotEmpty
    private List<Long> categories;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Float pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public List<Long> getCategories() {
        return categories;
    }

    public void setCategories(List<Long> categories) {
        this.categories = categories;
    }
}
