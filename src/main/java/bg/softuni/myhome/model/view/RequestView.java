package bg.softuni.myhome.model.view;

import bg.softuni.myhome.model.enums.StatusEnum;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class RequestView {

    private long id;

    private String clientName;

    private String email;

    private String phone;

    private String message;

    private String notes;

    private StatusEnum status;

    public long getId() {
        return id;
    }

    public RequestView setId(long id) {
        this.id = id;
        return this;
    }

    public String getClientName() {
        return clientName;
    }

    public RequestView setClientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RequestView setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public RequestView setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public RequestView setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public RequestView setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public RequestView setStatus(StatusEnum status) {
        this.status = status;
        return this;
    }
}
