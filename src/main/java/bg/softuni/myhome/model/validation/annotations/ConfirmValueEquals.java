package bg.softuni.myhome.model.validation.annotations;
import bg.softuni.myhome.model.validation.ConfirmValueValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ConfirmValueValidator.class)
public @interface ConfirmValueEquals {

    String message() default "Value should be the same";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
