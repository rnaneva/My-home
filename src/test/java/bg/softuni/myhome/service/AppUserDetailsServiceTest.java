package bg.softuni.myhome.service;

import bg.softuni.myhome.model.entities.UserEntity;
import bg.softuni.myhome.model.entities.UserRoleEntity;
import bg.softuni.myhome.model.enums.UserRoleEnum;
import bg.softuni.myhome.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class
AppUserDetailsServiceTest {

    private UserRoleEntity TEST_USER_ROLE ;

    private AppUserDetailsService testAppUserDetailsService;

    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp() {
        testAppUserDetailsService = new AppUserDetailsService(mockUserRepository);
        this.TEST_USER_ROLE = new UserRoleEntity().setRole(UserRoleEnum.USER);
    }

    @Test
    void test_LoadUserByUsername_returnsFoundUser() {

        UserEntity testUser = createTestUser();

        when(mockUserRepository.findByUsername("user"))
                .thenReturn(Optional.of(testUser));

        UserDetails userDetails =
                testAppUserDetailsService.loadUserByUsername("user");

        assertNotNull(userDetails);
        assertEquals("user", userDetails.getUsername());
        assertEquals(testUser.getPassword(), userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());

        assertTrue(roleExists(userDetails.getAuthorities(), "ROLE_USER"));
        assertFalse(roleExists(userDetails.getAuthorities(), "ROLE_ADMIN"));


    }

    @Test
    void test_LoadUserByUsername_ThrowsForNonExistingUser() {
     assertThrows(UsernameNotFoundException.class,
             ()-> testAppUserDetailsService.loadUserByUsername("not_existing"));
    }



    private boolean roleExists(Collection<? extends GrantedAuthority> authorities, String role) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority).toList()
                .contains(role);
    }

    private UserEntity createTestUser(){
        return new UserEntity()
                .setUsername("user")
                .setPassword("pass")
                .setRoles(List.of(TEST_USER_ROLE))
                .setId(1L);

    }

}
