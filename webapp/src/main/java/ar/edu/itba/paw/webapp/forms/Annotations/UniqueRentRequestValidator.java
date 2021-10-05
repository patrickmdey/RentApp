package ar.edu.itba.paw.webapp.forms.Annotations;

import ar.edu.itba.paw.interfaces.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UniqueRentRequestValidator implements ConstraintValidator<UniqueRentRequest, Object> {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private RentService rentService;

    private String startDate;
    private String endDate;
    private String renterId;
    private String articleId;

    @Override
    public void initialize(UniqueRentRequest uniqueRentRequest) {
        this.startDate = uniqueRentRequest.startDate();
        this.endDate = uniqueRentRequest.endDate();
        this.renterId = uniqueRentRequest.renterId();
        this.articleId = uniqueRentRequest.articleId();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        boolean toReturn = false;
        try {
            Date start = DATE_FORMAT.parse(getFieldValue(o, startDate));
            Date end = DATE_FORMAT.parse(getFieldValue(o, endDate));

            Long renter = new Long(getFieldValue(o, renterId));
            Long article = new Long(getFieldValue(o, articleId));

            toReturn = !rentService.isPresentSameDate(renter, article, start, end);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!toReturn) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Cant send two requests for the same article and time period")
                    .addNode(articleId).addConstraintViolation();
        }
        return toReturn;
    }

    private String getFieldValue(Object object, String fieldName) throws Exception {
        Class<?> clazz = object.getClass();
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return (String) field.get(object);
    }

}
