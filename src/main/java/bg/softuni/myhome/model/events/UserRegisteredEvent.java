package bg.softuni.myhome.model.events;

import org.springframework.context.ApplicationEvent;

public class UserRegisteredEvent extends ApplicationEvent {

    private final String userEmail;
    private final String username;
    private final String visibleId;

    public UserRegisteredEvent(Object source, String userEmail, String username, String visibleId) {
        super(source);
        this.userEmail = userEmail;
        this.username = username;
        this.visibleId = visibleId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUsername() {
        return username;
    }

    public String getVisibleId() {
        return visibleId;
    }
}
