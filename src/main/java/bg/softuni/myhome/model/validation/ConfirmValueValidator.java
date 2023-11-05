package bg.softuni.myhome.model.validation;

import bg.softuni.myhome.model.dto.NewPasswordDTO;
import bg.softuni.myhome.model.validation.annotations.ConfirmValueEquals;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ConfirmValueValidator implements ConstraintValidator<ConfirmValueEquals, NewPasswordDTO> {


    @Override
    public boolean isValid(NewPasswordDTO newPasswordDTO, ConstraintValidatorContext context) {

        return newPasswordDTO.getNewPassword().equals(newPasswordDTO.getConfirmNewPassword());
    }

}
