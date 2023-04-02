package bg.softuni.myhome.model.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AgencyNameValidator.class)
public @interface UniqueAgencyName {

    String message() default "Agency already has a profile";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
