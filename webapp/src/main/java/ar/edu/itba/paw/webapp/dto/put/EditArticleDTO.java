package ar.edu.itba.paw.webapp.dto.put;

import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class EditArticleDTO {

    @NotEmpty(message = "NotEmpty.createArticleForm.name")
    @Size(min = 5, max = 50, message = "Size.createArticleForm.name")
    private String title;

    @NotEmpty(message = "NotEmpty.createArticleForm.description")
    @Size(min = 10, max = 700, message = "Size.createArticleForm.description")
    private String description;

    @NotNull(message = "NotNull.createArticleForm.pricePerDay")
    @Min(value = 1, message = "Min.createArticleForm.pricePerDay")
    private Float pricePerDay;

    @NotEmpty(message = "NotEmpty.createArticleForm.categories")
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
