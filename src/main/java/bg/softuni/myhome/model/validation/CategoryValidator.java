package bg.softuni.myhome.model.validation;

import bg.softuni.myhome.model.validation.annotations.UniqueCategory;
import bg.softuni.myhome.service.CategoryService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CategoryValidator implements ConstraintValidator<UniqueCategory, String> {

    private final CategoryService categoryService;

    public CategoryValidator(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return categoryService.findByName(value) == null;
    }
}
