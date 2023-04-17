package bg.softuni.myhome.web;

import bg.softuni.myhome.model.dto.UserRegisterDTO;
import bg.softuni.myhome.service.UserRoleService;
import bg.softuni.myhome.service.UserService;
import com.cloudinary.Cloudinary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RegisterControllerIT {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Cloudinary cloudinary;


    @Test
    @WithAnonymousUser
    void test_getRegister() throws Exception {
        mockMvc.perform(get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }



    @Test
    @WithAnonymousUser
    void test_postRegister_fail() throws Exception {

        UserRegisterDTO userRegisterDTO = dtoInvalidEmail();

        mockMvc.perform(post("/users/register")
                        .flashAttr("userRegisterDTO", userRegisterDTO)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("register"));
    }

    private UserRegisterDTO dtoInvalidEmail(){
        return new UserRegisterDTO()
                .setEmail("email@abv.bg")
                .setUsername("username")
                .setNames("namesnames")
                .setPassword("password")
                .setConfirmPassword("password");
    }
}
