package travelagency.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CateringValidator.class)
public @interface Catering {
    String message() default "must be FULL, HALF or NOTHING";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
