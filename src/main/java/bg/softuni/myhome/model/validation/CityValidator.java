package bg.softuni.myhome.model.validation;

import bg.softuni.myhome.model.validation.annotations.UniqueCity;
import bg.softuni.myhome.service.CityService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;



public class CityValidator implements ConstraintValidator<UniqueCity, String> {

    private CityService cityService;

    public CityValidator(CityService cityService) {
        this.cityService = cityService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return cityService.findByName(value) == null;
    }
}
