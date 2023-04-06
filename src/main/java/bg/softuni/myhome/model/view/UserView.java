package bg.softuni.myhome.model.view;


import bg.softuni.myhome.model.enums.UserRoleEnum;


public class UserView {

    private long id;
    private String names;
    private String email;
    private String username;
    private UserRoleEnum role;
    private String updateDate;


    public long getId() {
        return id;
    }

    public UserView setId(long id) {
        this.id = id;
        return this;
    }

    public String getNames() {
        return names;
    }

    public UserView setNames(String names) {
        this.names = names;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserView setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserView setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public UserView setRole(UserRoleEnum role) {
        this.role = role;
        return this;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public UserView setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
        return this;
    }
}
