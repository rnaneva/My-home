package bg.softuni.myhome.model.events;

import org.springframework.context.ApplicationEvent;

public class PasswordChangedEvent extends ApplicationEvent {

    private final String userEmail;
    private final String code;


    public PasswordChangedEvent(Object source, String userEmail, String code) {
        super(source);
        this.userEmail = userEmail;
        this.code = code;

    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getCode() {
        return code;
    }

}
