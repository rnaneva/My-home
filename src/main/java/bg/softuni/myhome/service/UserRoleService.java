package bg.softuni.myhome.service;

import bg.softuni.myhome.exception.ObjectNotFoundException;
import bg.softuni.myhome.model.entities.UserRoleEntity;
import bg.softuni.myhome.model.enums.UserRoleEnum;
import bg.softuni.myhome.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public UserRoleEntity findByRole(UserRoleEnum userRoleEnum){
        return userRoleRepository.findByRole(userRoleEnum)
                .orElseThrow(() ->  new ObjectNotFoundException("findByRole", userRoleEnum));
    }



}
