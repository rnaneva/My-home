package bg.softuni.myhome.model.dto;

import bg.softuni.myhome.model.validation.annotations.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailDTO {

    @NotBlank(message = "Please provide a valid email")
    @Email
    private String email;

    public String getEmail() {
        return email;
    }

    public EmailDTO setEmail(String email) {
        this.email = email;
        return this;
    }
}
