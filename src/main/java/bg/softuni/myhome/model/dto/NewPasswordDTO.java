package bg.softuni.myhome.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class NewPasswordDTO {

    @NotNull
    @Size(min = 4, max = 10, message = "Required-between 4 and 10 symbols")
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public NewPasswordDTO setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }
}
