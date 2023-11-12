package bg.softuni.myhome.model.entities;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "password_change_codes")
public class PasswordChangeCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String code;

    private Instant created;

    @ManyToOne
    private UserEntity user;

    public long getId() {
        return id;
    }

    public PasswordChangeCode setId(long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return code;
    }

    public PasswordChangeCode setCode(String code) {
        this.code = code;
        return this;
    }

    public Instant getCreated() {
        return created;
    }

    public PasswordChangeCode setCreated(Instant created) {
        this.created = created;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public PasswordChangeCode setUser(UserEntity user) {
        this.user = user;
        return this;
    }
}
