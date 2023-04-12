package bg.softuni.myhome.model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;

public class AppUserDetails extends User {

    private long id;
    private String names;
    private String email;
    private String visibleId;



    public AppUserDetails(String username, String password,
                          Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public String getNames() {
        return names;
    }

    public AppUserDetails setNames(String names) {
        this.names = names;
        return this;
    }

    public String getVisibleId() {
        return visibleId;
    }

    public AppUserDetails setVisibleId(String visibleId) {
        this.visibleId = visibleId;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AppUserDetails setEmail(String email) {
        this.email = email;
        return this;
    }

    public long getId() {
        return id;
    }

    public AppUserDetails setId(long id) {
        this.id = id;
        return this;
    }
}
