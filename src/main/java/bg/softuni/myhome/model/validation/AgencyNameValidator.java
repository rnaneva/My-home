package bg.softuni.myhome.model.validation;

import bg.softuni.myhome.service.AgencyService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AgencyNameValidator implements ConstraintValidator<UniqueAgencyName, String> {

    private AgencyService agencyService;

    public AgencyNameValidator(AgencyService agencyService) {
        this.agencyService = agencyService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return agencyService.findByName(value) == null;
    }
}
