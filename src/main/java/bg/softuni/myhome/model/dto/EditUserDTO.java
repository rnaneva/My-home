package bg.softuni.myhome.model.dto;

import bg.softuni.myhome.model.enums.UserRoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EditUserDTO {

    private long id;

    @NotBlank
    private String names;

    @NotBlank
    private String email;

    @NotBlank
    private String username;

    @NotNull
    private UserRoleEnum role;



    public String getNames() {
        return names;
    }

    public EditUserDTO setNames(String names) {
        this.names = names;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public EditUserDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public EditUserDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public long getId() {
        return id;
    }

    public EditUserDTO setId(long id) {
        this.id = id;
        return this;
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public EditUserDTO setRole(UserRoleEnum role) {
        this.role = role;
        return this;
    }
}
