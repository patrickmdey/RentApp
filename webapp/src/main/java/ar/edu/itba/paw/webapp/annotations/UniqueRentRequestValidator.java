package ar.edu.itba.paw.webapp.annotations;

import ar.edu.itba.paw.interfaces.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.time.LocalDate;

public class UniqueRentRequestValidator implements ConstraintValidator<UniqueRentRequest, Object> {
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
            LocalDate start = (LocalDate) getFieldValue(o, startDate);
            LocalDate end = (LocalDate) getFieldValue(o, endDate);

            Long renter = (Long) getFieldValue(o, renterId);
            Long article = Long.valueOf((Integer) getFieldValue(o, articleId));

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

    private Object getFieldValue(Object object, String fieldName) throws Exception {
        Class<?> clazz = object.getClass();
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }

}
