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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AgencyOffersControllerIT {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private Cloudinary cloudinary;

    @Test
    @WithMockUser(username = "testUsername", authorities = {ROLE_MODERATOR})
    void test_getActiveOffers() throws Exception {

        mockMvc.perform(get("/agency/offers/active/{id}", "id"))
                .andExpect(status().isOk())
                .andExpect(view().name("agency-offers"));
    }

    @Test
    @WithMockUser(username = "testUsername", authorities = {ROLE_MODERATOR})
    void test_getInactiveOffers() throws Exception {

        mockMvc.perform(get("/agency/offers/inactive/{id}", "id"))
                .andExpect(status().isOk())
                .andExpect(view().name("agency-offers"));
    }


}
