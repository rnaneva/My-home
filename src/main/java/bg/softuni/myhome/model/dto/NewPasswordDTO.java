package bg.softuni.myhome.model.dto;

import bg.softuni.myhome.model.validation.annotations.ConfirmValueEquals;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@ConfirmValueEquals(message = "Passwords should match")
public class NewPasswordDTO {

    @Size(min = 4, max = 10, message = "Required-between 4 and 10 symbols")
    private String newPassword;

    private String confirmNewPassword;

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public NewPasswordDTO setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
        return this;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public NewPasswordDTO setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }
}
