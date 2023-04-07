package bg.softuni.myhome.model.validation.annotations;

import bg.softuni.myhome.model.validation.CategoryValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CategoryValidator.class)
public @interface UniqueCategory {

    String message() default "Category already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
