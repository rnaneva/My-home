package bg.softuni.myhome.web;

import bg.softuni.myhome.model.dto.OfferPageThreeDTO;
import bg.softuni.myhome.model.dto.OfferPageTwoDTO;
import bg.softuni.myhome.service.*;
import bg.softuni.myhome.util.EntitiesDataUtils;
import bg.softuni.myhome.util.OfferDataUtils;
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
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OfferAddControllerIT {

    @MockBean
    private OfferPageOneService offerPageOneService;
    @MockBean
    private OfferService offerService;


    @MockBean
    private OfferPageTwoService offerPageTwoService;
    @MockBean
    private PictureService pictureService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Cloudinary cloudinary;



    @Test
    @WithMockUser(username = "username",password = "pass", authorities = {ROLE_MODERATOR})
    void test_getAddOfferPageOne() throws Exception {
        mockMvc.perform(get("/agency/offers/add/one/{id}", "id"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-offer-one"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attributeExists("constructionEnums"))
                .andExpect(model().attributeExists("heatingEnums"))
                .andExpect(model().attributeExists("offerTypeEnums"));
    }

    @Test
    @WithMockUser(username = "username",password = "pass", authorities = {ROLE_MODERATOR})
    void test_getAddOfferPageTwo() throws Exception {

        mockMvc.perform(get("/agency/offers/add/two/{id}", "id"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-offer-two"))
                .andExpect(model().attributeExists("cities"))
                .andExpect(model().attributeExists("offerVisibleId"));
    }

    @Test
    @WithMockUser(username = "username",password = "pass", authorities = {ROLE_MODERATOR})
    void test_getAddOfferPageThree() throws Exception {

        mockMvc.perform(get("/agency/offers/edit/three/{id}", "id"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-offer-three"))
                .andExpect(model().attributeExists("offerVisibleId"))
                .andExpect(model().attributeExists("pictures"));
    }



    @Test
    @WithMockUser(username = "username",password = "pass", authorities = {ROLE_MODERATOR})
    void test_postAddOfferPageTwo() throws Exception {

        OfferPageTwoDTO offerPageTwoDTO = OfferDataUtils.offerPageTwoDTO();

        mockMvc.perform(post("/agency/offers/add/two/{id}", "id")
                        .flashAttr("offerPageTwoDTO", offerPageTwoDTO)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/agency/offers/edit/three/id"));

    }

    @Test
    @WithMockUser(username = "username",password = "pass", authorities = {ROLE_MODERATOR})
    void test_postAddOfferPageTwo_fail() throws Exception {


        mockMvc.perform(post("/agency/offers/add/two/{id}", "id")
                        .param("address", "address")
                        .param("cityName", "cityName")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/agency/offers/add/two/id"));

    }


    @Test
    @WithMockUser(username = "username",password = "pass", authorities = {ROLE_MODERATOR})
    void test_postAddOfferPageThree() throws Exception {

        OfferPageThreeDTO offerPageThreeDTO = OfferDataUtils.offerPageThreeDTO();
        mockGetOfferEntity();

        mockMvc.perform(post("/agency/offers/add/three/{id}", "id")
                        .flashAttr("offerPageThreeDTO", offerPageThreeDTO)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/agency/offers/edit/three/id"));

    }

    private void mockGetOfferEntity() {
        when(offerService.getOfferById("id")).thenReturn(EntitiesDataUtils.getOffer());
    }


}
