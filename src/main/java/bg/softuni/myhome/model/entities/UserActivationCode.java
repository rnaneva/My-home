package bg.softuni.myhome.model.entities;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name= "user_activation_codes")
public class UserActivationCode {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private long Id;

    private String activationCode;

    private Instant created;

    @ManyToOne
    private UserEntity user;


    public long getId() {
        return Id;
    }

    public UserActivationCode setId(long id) {
        Id = id;
        return this;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public UserActivationCode setActivationCode(String activationCode) {
        this.activationCode = activationCode;
        return this;
    }

    public Instant getCreated() {
        return created;
    }

    public UserActivationCode setCreated(Instant created) {
        this.created = created;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public UserActivationCode setUser(UserEntity user) {
        this.user = user;
        return this;
    }
}
