package bg.softuni.myhome.model.dto;


import bg.softuni.myhome.validation.UniqueEmail;
import bg.softuni.myhome.validation.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class UserRegisterDTO {

    @NotNull
    @Size(min = 5, max = 20, message = "Required-between 5 and 20 symbols")
    private String names;

    @NotEmpty(message = "Please provide a valid email")
    @Email
    @UniqueEmail
    private String email;

    @NotNull
    @Size(min = 3, max = 10, message = "Required-between 3 and 10 symbols")
    @UniqueUsername
    private String username;

    @NotNull
    @Size(min = 4, max = 10, message = "Required-between 4 and 10 symbols")
    private String password;

    @NotNull
    @Size(min = 4, max = 10, message = "Required-between 4 and 10 symbols")
    private String confirmPassword;

    public String getNames() {
        return names;
    }

    public UserRegisterDTO setNames(String names) {
        this.names = names;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegisterDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserRegisterDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterDTO setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
