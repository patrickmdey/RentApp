package ar.edu.itba.paw.webapp.forms;

import ar.edu.itba.paw.webapp.forms.Annotations.GreaterDate;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;


@GreaterDate(baseField = "startDate", matchField = "endDate")
public class RentProposalForm {

    @NotNull
    @NotEmpty
    private String startDate;

    @NotNull
    @NotEmpty
    private String endDate;

    @NotEmpty
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

}
