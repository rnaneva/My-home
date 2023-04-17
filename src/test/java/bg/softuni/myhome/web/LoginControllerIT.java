package bg.softuni.myhome.web;

import com.cloudinary.Cloudinary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginControllerIT {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Cloudinary cloudinary;


    @Test
    @WithAnonymousUser
    void test_getLogin() throws Exception {
        mockMvc.perform(get("/users/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
   void test_failLogin() throws Exception {
        mockMvc.perform(post("/users/login-error")
                        .param("username", "username")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("login"));
   }

    @Test
    @WithMockUser(username = "username", password = "password")
    void test_postLogin() throws Exception {
        mockMvc.perform(formLogin("/users/login")
                        .user( "username")
                        .password("password")
                )
                .andExpect(authenticated());
    }
}
