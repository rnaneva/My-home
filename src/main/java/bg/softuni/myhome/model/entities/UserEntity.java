package bg.softuni.myhome.model.entities;

import bg.softuni.myhome.model.enums.StatusEnum;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.*;


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
    private LocalDate created;


    @OneToMany
    private Set <OfferEntity> favourites;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;



    public UserEntity() {
        this.roles = new ArrayList<>();
        this.created = LocalDate.now();
        this.visibleId = String.valueOf(UUID.randomUUID());
        this.favourites = new HashSet<>();
    }

    public StatusEnum getStatus() {
        return status;
    }

    public UserEntity setStatus(StatusEnum status) {
        this.status = status;
        return this;
    }


    public Long getId() {
        return id;
    }

    public UserEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getCreated() {
        return created;
    }



    public UserEntity setCreated(LocalDate created) {
        this.created = created;
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

    public Set<OfferEntity> getFavourites() {
        return favourites;
    }

    public UserEntity setFavourites(Set<OfferEntity> favourites) {
        this.favourites = favourites;
        return this;
    }

    public void addFavourite(OfferEntity favourite){
        this.favourites.add(favourite);
    }

    public void removeFavourite(OfferEntity favourite){
        this.favourites.remove(favourite);
    }

    public boolean isFavourite(OfferEntity offer){
        if(this.favourites.isEmpty()){
            return false;
        }
        return this.favourites.contains(offer);
    }


}
