package bg.softuni.myhome.model.entities;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


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
    private LocalDate lastUpdatedOn;

    private String oneTimePass;



    public UserEntity() {
        this.roles = new ArrayList<>();
        this.lastUpdatedOn = LocalDate.now();
        this.visibleId = String.valueOf(UUID.randomUUID());
    }

    public String getOneTimePass() {
        return oneTimePass;
    }

    public UserEntity setOneTimePass(String oneTimePass) {
        this.oneTimePass = oneTimePass;
        return this;
    }

    public Long getId() {
        return id;
    }

    public UserEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getLastUpdatedOn() {
        return lastUpdatedOn;
    }



    public UserEntity setLastUpdatedOn(LocalDate lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
        return this;
    }


    public String getVisibleId() {
        return visibleId;
    }

    public UserEntity setVisibleId(String visibleId) {
        this.visibleId = visibleId;
        return this;
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
