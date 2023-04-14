package bg.softuni.myhome.service;

import bg.softuni.myhome.exception.ObjectNotFoundException;
import bg.softuni.myhome.model.dto.UserRegisterDTO;

import bg.softuni.myhome.model.entities.UserEntity;
import bg.softuni.myhome.model.entities.UserRoleEntity;
import bg.softuni.myhome.model.enums.UserRoleEnum;
import bg.softuni.myhome.repository.UserRepository;
;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository mockUserRepository;
    private ModelMapper modelMapper = new ModelMapper();

    @Mock
    private PasswordEncoder mockPasswordEncoder;
    @Mock
    private UserRoleService mockUserRoleService;

    private UserService testUserService;
    @Captor
    private ArgumentCaptor<UserEntity> userEntityArgumentCaptor;

    @BeforeEach
    void setUp() {
        this.testUserService = new UserService(mockUserRepository, modelMapper,
                mockPasswordEncoder,mockUserRoleService);
    }

    @Test
    void test_registerUser_saveInvoked(){
        UserRegisterDTO dto = userRegisterDTO();
        UserRoleEnum roleEnum = UserRoleEnum.USER;

        when(mockPasswordEncoder.encode("1234")).thenReturn("hashed");
        when(mockUserRoleService.findByRole(roleEnum)).thenReturn(new UserRoleEntity().setRole(roleEnum));
        testUserService.registerUser(dto);
        verify(mockUserRepository).save(userEntityArgumentCaptor.capture());
        UserEntity user = userEntityArgumentCaptor.getValue();
        assertEquals("hashed", user.getPassword());
        assertEquals(dto.getEmail(), user.getEmail());
        assertEquals(roleEnum, user.getRoles().get(0).getRole());
    }

    @Test
    void test_findByUserVisibleId_UserFound_ReturnsUser(){
        String visibleId = "visibleId";
        String names = "Ivan Ivanov";
        UserEntity user = new UserEntity().setVisibleId(visibleId).setNames(names);

        when(mockUserRepository.findByVisibleId(visibleId)).thenReturn(Optional.ofNullable(user));

        UserEntity found = testUserService.findByUserVisibleId(visibleId);
        assertEquals(visibleId, found.getVisibleId());
        assertEquals(names, found.getNames());

    }

    @Test
    void test_findByUserVisibleId_NotFound_Throws(){
        assertThrows(ObjectNotFoundException.class,
                ()-> testUserService.findByUserVisibleId("not_existing"));

    }

    @Test
    void test_findById_Found_ReturnsUser(){

    }

    @Test
    void test_findById_NotFound_Throws(){

    }


    private UserRegisterDTO userRegisterDTO(){
        return new UserRegisterDTO()
                .setEmail("testEmail")
                .setNames("testNames")
                .setUsername("testUsername")
                .setPassword("1234")
                .setConfirmPassword("1234");
    }
}
