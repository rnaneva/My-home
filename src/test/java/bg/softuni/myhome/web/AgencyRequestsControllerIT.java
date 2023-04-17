package bg.softuni.myhome.web;

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

import static bg.softuni.myhome.commons.StaticVariables.ROLE_MODERATOR;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AgencyRequestsControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Cloudinary cloudinary;

    @Test
    @WithMockUser(username = "username",password = "pass", authorities = {ROLE_MODERATOR})
    void test_getNewRequests() throws Exception {

        mockMvc.perform(get("/agency/requests/new/{id}", "id"))
                .andExpect(status().isOk())
                .andExpect(view().name("agency-requests"));
    }


    @Test
    @WithMockUser(username = "username",password = "pass", authorities = {ROLE_MODERATOR})
    void test_getRepliedRequests() throws Exception {

        mockMvc.perform(get("/agency/requests/replied/{id}", "id"))
                .andExpect(status().isOk())
                .andExpect(view().name("agency-requests"));
    }

    @Test
    @WithMockUser(username = "username",password = "pass", authorities = {ROLE_MODERATOR})
    void test_getRejectedRequests() throws Exception {

        mockMvc.perform(get("/agency/requests/rejected/{id}", "id"))
                .andExpect(status().isOk())
                .andExpect(view().name("agency-requests"));
    }

    @Test
    @WithMockUser(username = "username",password = "pass", authorities = {ROLE_MODERATOR})
    void test_getInspectionRequests() throws Exception {

        mockMvc.perform(get("/agency/requests/inspection/{id}", "id"))
                .andExpect(status().isOk())
                .andExpect(view().name("agency-requests"));
    }

}
