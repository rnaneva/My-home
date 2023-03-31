package bg.softuni.myhome.commons;

import bg.softuni.myhome.model.AppUserDetails;
import bg.softuni.myhome.model.entities.UserEntity;
import bg.softuni.myhome.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommonService {


    public String createVisibleId(){
        return UUID.randomUUID().toString();
    }
}
