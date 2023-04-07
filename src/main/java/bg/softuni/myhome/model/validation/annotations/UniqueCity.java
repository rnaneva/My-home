package bg.softuni.myhome.model.validation.annotations;

import bg.softuni.myhome.model.validation.CityValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CityValidator.class)
public @interface UniqueCity {

    String message() default "City already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
