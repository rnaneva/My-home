package bg.softuni.myhome.model.dto;


import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

public class RequestDTO {

    @NotNull
    @Size(min = 5, max = 20, message = "Required-between 5 and 20 symbols")
    private String clientName;

    @NotBlank(message = "Please provide a valid email")
    @Email
    private String email;

    @NotNull(message = "Phone number is required")
    @Pattern(regexp = "^[+]?[0-9]+$", message = "Please provide a valid phone number")
    private String phone;

    @Column(columnDefinition = "LONGTEXT")
    @NotBlank(message = "Please write your questions here")
    private String message;


    public String getClientName() {
        return clientName;
    }

    public RequestDTO setClientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RequestDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public RequestDTO setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public RequestDTO setMessage(String message) {
        this.message = message;
        return this;
    }
}
