package bg.softuni.myhome.service;

import bg.softuni.myhome.exception.ObjectNotFoundException;
import bg.softuni.myhome.model.dto.EditUserDTO;
import bg.softuni.myhome.model.dto.UserRegisterDTO;

import bg.softuni.myhome.model.entities.UserEntity;
import bg.softuni.myhome.model.entities.UserRoleEntity;
import bg.softuni.myhome.model.enums.UserRoleEnum;
import bg.softuni.myhome.model.view.UserView;
import bg.softuni.myhome.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository mockUserRepository;
    private ModelMapper modelMapper = new ModelMapper();

    @Mock
    private PasswordEncoder mockPasswordEncoder;
    @Mock
    private UserRoleService mockUserRoleService;

    @Mock
    private EmailService emailService;
    private UserService testUserService;
    @Captor
    private ArgumentCaptor<UserEntity> userEntityArgumentCaptor;

    @BeforeEach
    void setUp() {
        this.testUserService = new UserService(mockUserRepository, modelMapper,
                mockPasswordEncoder, mockUserRoleService, emailService);
    }

    @Test
    void test_registerUser_saveInvoked() {
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
    void test_findByUserVisibleId_UserFound_ReturnsUser() {
        String visibleId = "visibleId";
        String names = "Ivan Ivanov";
        UserEntity user = new UserEntity().setVisibleId(visibleId).setNames(names);

        when(mockUserRepository.findByVisibleId(visibleId)).thenReturn(Optional.ofNullable(user));

        UserEntity found = testUserService.findByUserVisibleId(visibleId);
        assertEquals(visibleId, found.getVisibleId());
        assertEquals(names, found.getNames());

    }

    @Test
    void test_findByUserVisibleId_NotFound_Throws() {
        assertThrows(ObjectNotFoundException.class,
                () -> testUserService.findByUserVisibleId("not_existing"));

    }

    @Test
    void test_getUserViewById_Found_ReturnsUserView() {
        long id = 1L;
        String username = "testUsername";
        UserEntity user = getUser();
        mockFindById(id, user);
        UserView found = testUserService.getUserViewById(id);
        assertEquals(id, found.getId());
        assertEquals(username, found.getUsername());
    }




    @Test
    void test_getUserViewById_NotFound_Throws() {
        assertThrows(ObjectNotFoundException.class, () -> testUserService.getUserViewById(1L));
    }


    @Test
    void test_editUser_userFound_edited(){
        UserEntity user = getUser();
        mockFindById(1L, user);
        UserRoleEnum newRoleEnum = UserRoleEnum.MODERATOR;
        EditUserDTO dto = editUserDTO();
        when(mockUserRoleService.findByRole(newRoleEnum)).thenReturn(setNewRole(newRoleEnum));
        testUserService.editUser(dto);
        verify(mockUserRepository).save(userEntityArgumentCaptor.capture());
        UserEntity editedUser = userEntityArgumentCaptor.getValue();
        assertEquals(user.getId(), editedUser.getId());
        assertEquals(dto.getEmail(), editedUser.getEmail());
        assertEquals(dto.getRole(), editedUser.getRoles().get(0).getRole());
    }



    @Test
    void test_editUser_userNotFound_NothingHappens(){
        testUserService.editUser(new EditUserDTO());
        verify(mockUserRepository, never()).save(new UserEntity());

    }


    private static UserRoleEntity setNewRole(UserRoleEnum newRoleEnum) {
        return new UserRoleEntity().setRole(newRoleEnum);
    }

    private void mockFindById(long id, UserEntity user) {
        when(mockUserRepository.findById(id)).thenReturn(Optional.ofNullable(user));
    }

    private static UserEntity getUser() {
        return new UserEntity().setId(1L).setUsername("testUsername").setRoles(getRoles());
    }

    private EditUserDTO editUserDTO(){
        return new EditUserDTO()
                .setEmail("newEmail")
                .setId(1L)
                .setRole(UserRoleEnum.MODERATOR);
    }

    private UserRegisterDTO userRegisterDTO() {
        return new UserRegisterDTO()
                .setEmail("testEmail")
                .setNames("testNames")
                .setUsername("testUsername")
                .setPassword("1234")
                .setConfirmPassword("1234");
    }

    private static List<UserRoleEntity> getRoles() {
        return List.of(new UserRoleEntity().setRole(UserRoleEnum.USER));
    }



}
