package bg.softuni.myhome.web;


import bg.softuni.myhome.model.dto.UserRequestDTO;
import bg.softuni.myhome.model.entities.OfferEntity;
import bg.softuni.myhome.service.OfferService;
import bg.softuni.myhome.util.OfferDataUtils;
import com.cloudinary.Cloudinary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OffersControllerIT {

    @MockBean
    private OfferService offerService;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private Cloudinary cloudinary;

    @Test
    @WithAnonymousUser
    void test_getRent() throws Exception {

        mockMvc.perform(get("/offers/rent"))
                .andExpect(status().isOk())
                .andExpect(view().name("rent-offers"))
                .andExpect(model().attributeExists("rentOffers"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attributeExists("cities"));
    }

    @Test
    @WithAnonymousUser
    void test_getSale() throws Exception {

        mockMvc.perform(get("/offers/sale"))
                .andExpect(status().isOk())
                .andExpect(view().name("sale-offers"))
                .andExpect(model().attributeExists("saleOffers"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attributeExists("cities"));
    }


    @Test
    void test_getOfferDetails() throws Exception {


        mockFindOfferDetailedVied();

        mockMvc.perform(get("/offers/{visibleId}", "id"))
                .andExpect(status().isOk())
                .andExpect(view().name("offer-details"));
    }



    @Test
    void test_postRequest_fail() throws Exception {

        UserRequestDTO userRequestDTO = dtoIncorrectEmail();

        mockMvc.perform(post("/offers/{visibleId}", "id")
                        .flashAttr("userRequestDTO", userRequestDTO)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/offers/id"));
    }

    @Test
    void test_postRequest() throws Exception {

        UserRequestDTO userRequestDTO = userRequestDTO();
        mockFindOfferEntity();
        mockMvc.perform(post("/offers/{visibleId}", "id")
                        .flashAttr("userRequestDTO", userRequestDTO)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("successful-message"));
    }


    private UserRequestDTO userRequestDTO(){
        return new UserRequestDTO()
                .setPhone("0899")
                .setMessage("message")
                .setEmail("email@abv.bg")
                .setClientName("name1");
}

    private UserRequestDTO dtoIncorrectEmail(){
        return new UserRequestDTO()
                .setPhone("0899")
                .setMessage("message")
                .setEmail("email")
                .setClientName("name1");
    }

    private void mockFindOfferDetailedVied() {
        when( offerService.findDetailedOfferByVisibleId("id"))
                .thenReturn(OfferDataUtils.offerDetailsView());
    }

    private void mockFindOfferEntity() {
        when(offerService.getOfferById("id")).thenReturn(new OfferEntity().setId(1L));
    }




}
