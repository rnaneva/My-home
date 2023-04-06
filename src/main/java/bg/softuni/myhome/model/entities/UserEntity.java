package bg.softuni.myhome.model.entities;

import bg.softuni.myhome.model.enums.StatusEnum;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Column(nullable = false)
    private String names;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<UserRoleEntity> roles;

    @Column(nullable = false)
    private String visibleId;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate updateDate;

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public UserEntity setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public String getVisibleId() {
        return visibleId;
    }

    public UserEntity setVisibleId(String visibleId) {
        this.visibleId = visibleId;
        return this;
    }

    public UserEntity() {
        this.roles = new ArrayList<>();
    }

    public String getNames() {
        return names;
    }

    public UserEntity setNames(String names) {
        this.names = names;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public List<UserRoleEntity> getRoles() {
        return Collections.unmodifiableList(roles);
    }

    public UserEntity setRoles(List<UserRoleEntity> roles) {
        this.roles = roles;
        return this;
    }

    public void addRole(UserRoleEntity role) {
        this.roles.add(role);
    }


}
