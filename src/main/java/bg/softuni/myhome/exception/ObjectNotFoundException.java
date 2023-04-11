package bg.softuni.myhome.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends ServiceException{

    public ObjectNotFoundException(String methodName, Object object) {
        super(methodName, object);
    }
}
