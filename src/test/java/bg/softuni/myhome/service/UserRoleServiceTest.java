package bg.softuni.myhome.service;

import bg.softuni.myhome.exception.ObjectNotFoundException;
import bg.softuni.myhome.model.entities.UserRoleEntity;
import bg.softuni.myhome.model.enums.UserRoleEnum;
import bg.softuni.myhome.repository.UserRoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRoleServiceTest {

    @Mock
    private UserRoleRepository mockUserRoleRepository;

    @InjectMocks
    private UserRoleService testUserRoleService;

    @Test
    void test_findByRole_RoleFound_Returned(){
        UserRoleEnum roleEnum = UserRoleEnum.ADMIN;
        UserRoleEntity role = new UserRoleEntity().setRole(roleEnum);
        when(mockUserRoleRepository.findByRole(roleEnum))
                .thenReturn(Optional.ofNullable(role));

        UserRoleEntity found = testUserRoleService.findByRole(roleEnum);
        Assertions.assertEquals(roleEnum, found.getRole());
    }

    @Test
    void test_findByRole_NotFound_Throws(){
        Assertions.assertThrows(ObjectNotFoundException.class, ()->
                testUserRoleService.findByRole(UserRoleEnum.ADMIN));

    }
}
