package ar.edu.itba.paw.webapp.forms;

import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

public class EditArticleForm {

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String description;

    @NotNull
    @Min(1)
    private Float pricePerDay;

    @NotEmpty
    private List<Long> categories;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Long> getCategories(){
        return categories;
    }

    public void setCategories(List<Long> categories) {
        this.categories = categories;
    }

}
