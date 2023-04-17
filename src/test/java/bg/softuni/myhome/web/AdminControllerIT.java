package bg.softuni.myhome.web;

import bg.softuni.myhome.model.dto.EditUserDTO;
import bg.softuni.myhome.model.enums.UserRoleEnum;
import bg.softuni.myhome.model.view.UserView;

import bg.softuni.myhome.repository.UserRepository;
import bg.softuni.myhome.service.UserService;
import bg.softuni.myhome.util.EntitiesDataUtils;
import com.cloudinary.Cloudinary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Optional;

import static bg.softuni.myhome.commons.StaticVariables.ROLE_ADMIN;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AdminControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Cloudinary cloudinary;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;



    @Test
    @WithMockUser(username = "testUsername", authorities = {ROLE_ADMIN})
    void test_admin() throws Exception {

        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    @WithMockUser(username = "testUsername", authorities = {ROLE_ADMIN})
    void test_getEditUserAccount() throws Exception {
        mockGetUserView();
        mockMvc.perform(get("/admin/users/edit/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-edit-user"))
                .andExpect(model().attributeExists("user"));
    }



    @Test
    @WithMockUser(username = "testUsername", authorities = {ROLE_ADMIN})
    void test_patchEditUserAccount_Fails() throws Exception {

        mockMvc.perform(patch("/admin/users/edit/{id}", 1)
                        .param("email", "email")
                        .param("username", "username")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users/edit/1"));
    }
    @Test
    @WithMockUser(username = "testUsername", authorities = {ROLE_ADMIN})
    void test_patchEditUserAccount_() throws Exception {

        mockGetUser();
        EditUserDTO editUserDTO = editUserDTO();
        mockMvc.perform(patch("/admin/users/edit/{id}", 1)
                .flashAttr("editUserDTO", editUserDTO)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));
    }


    @Test
    @WithMockUser(username = "testUsername", authorities = {ROLE_ADMIN})
    void test_getNewCategory() throws Exception {
        mockMvc.perform(get("/admin/categories/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-new-category"));
    }

    @Test
    @WithMockUser(username = "testUsername", authorities = {ROLE_ADMIN})
    void test_getNewCity() throws Exception {
        mockMvc.perform(get("/admin/cities/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-new-city"));
    }


    @Test
    @WithMockUser(username = "testUsername", authorities = {ROLE_ADMIN})
    void test_postNewCategory() throws Exception {

        mockMvc.perform(post("/admin/categories/new")
                        .param("name", "name")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));

    }


    @Test
    @WithMockUser(username = "testUsername", authorities = {ROLE_ADMIN})
    void test_postNewCity() throws Exception {

        mockMvc.perform(post("/admin/cities/new")
                        .param("name", "name")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));

    }

    private UserView userView() {
        return new UserView()
                .setUsername("username1")
                .setNames("names")
                .setEmail("email1")
                .setRole(UserRoleEnum.USER)
                .setUpdateDate("date")
                .setId(1L);
    }

    private EditUserDTO editUserDTO(){
        return new EditUserDTO()
                .setNames("names")
                .setEmail("email")
                .setUsername("username")
                .setRole(UserRoleEnum.MODERATOR);
    }

    private void mockGetUserView() {
        when(userService.getUserViewById(1)).thenReturn(userView());
    }


    private void mockGetUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(EntitiesDataUtils.addUser()));
    }
}
