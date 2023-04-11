package bg.softuni.myhome.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserNotAuthorizedException extends ServiceException {

    public UserNotAuthorizedException(String methodName, Object object) {
        super(methodName, object);
    }
}
