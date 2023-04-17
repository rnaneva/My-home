package bg.softuni.myhome.web;

import bg.softuni.myhome.model.dto.OfferPageOneDTO;
import bg.softuni.myhome.model.dto.OfferPageThreeDTO;
import bg.softuni.myhome.model.dto.OfferPageTwoDTO;
import bg.softuni.myhome.model.entities.LocationEntity;
import bg.softuni.myhome.model.entities.OfferPageTwo;

import bg.softuni.myhome.service.LocationService;
import bg.softuni.myhome.service.OfferPageTwoService;
import bg.softuni.myhome.service.OfferService;
import bg.softuni.myhome.service.PictureService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OfferEditControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Cloudinary cloudinary;

    @MockBean
    private OfferService offerService;

    @MockBean
    private OfferPageTwoService offerPageTwoService;

    @MockBean
    private LocationService locationService;

    @MockBean
    private PictureService pictureService;

    @Test
    @WithMockUser(username = "username", password = "pass", authorities = {ROLE_MODERATOR})
    void test_getEditOfferPageOne() throws Exception {

        mockGetOfferEntity();
        mockMvc.perform(get("/agency/offers/edit/one/{id}", "id"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-offer-one"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attributeExists("offerVisibleId"))
                .andExpect(model().attributeExists("offerPageOne"));
    }



    @Test
    @WithMockUser(username = "username", password = "pass", authorities = {ROLE_MODERATOR})
    void test_postEditOfferPageOne() throws Exception {

        mockGetOfferEntity();
        OfferPageOneDTO offerPageOneDTO = OfferDataUtils.offerPageOneDTO();

        mockMvc.perform(put("/agency/offers/edit/one/{id}", "id")
                        .flashAttr("offerPageOneDTO", offerPageOneDTO)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/agency/offers/edit/two/id"));

    }

    @Test
    @WithMockUser(username = "username", password = "pass", authorities = {ROLE_MODERATOR})
    void test_getEditOfferPageTwo() throws Exception {
        OfferPageTwo pageTwo = EntitiesDataUtils.getTestOfferPageTwo();
        mockGetOfferPageTwoEntity(pageTwo);
        mockGetOfferPageTwoView(pageTwo);
        mockMvc.perform(get("/agency/offers/edit/two/{id}", "id"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-offer-two"))
                .andExpect(model().attributeExists("cities"))
                .andExpect(model().attributeExists("offerPageTwo"))
                .andExpect(model().attributeExists("offerVisibleId"));
    }


    @Test
    @WithMockUser(username = "username", password = "pass", authorities = {ROLE_MODERATOR})
    void test_postEditOfferPageTwo() throws Exception {

        mockGetOfferEntity();
        mockSaveLocation();

        OfferPageTwoDTO offerPageTwoDTO = OfferDataUtils.offerPageTwoDTO();
        mockMvc.perform(put("/agency/offers/edit/two/{id}", "id")
                        .flashAttr("offerPageTwoDTO", offerPageTwoDTO)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/agency/offers/edit/three/id"));

    }



    @Test
    @WithMockUser(username = "username", password = "pass", authorities = {ROLE_MODERATOR})
    void test_postEditOfferPageTwo_fail() throws Exception {

        mockGetOfferEntity();
        mockSaveLocation();

        mockMvc.perform(put("/agency/offers/edit/two/{id}", "id")
                        .param("locationCityName", "locationCityName")
                        .param("locationAddress", "locationAddress")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/agency/offers/edit/two/id"));

    }

    @Test
    @WithMockUser(username = "username",password = "pass", authorities = {ROLE_MODERATOR})
    void test_getEditOfferPageThree() throws Exception {

        mockMvc.perform(get("/agency/offers/edit/three/{id}", "id"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-offer-three"))
                .andExpect(model().attributeExists("offerVisibleId"));
    }

    @Test
    @WithMockUser(username = "username",password = "pass", authorities = {ROLE_MODERATOR})
    void test_postEditOfferPageThree() throws Exception {

        OfferPageThreeDTO offerPageThreeDTO = OfferDataUtils.offerPageThreeDTO();

        mockMvc.perform(post("/agency/offers/edit/three/{id}", "id")
                        .flashAttr("offerPageThreeDTO", offerPageThreeDTO)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/agency/offers/edit/three/id"));

    }


    private void mockGetOfferEntity() {
        when(offerService.getOfferById("id")).thenReturn(EntitiesDataUtils.getOffer());
    }

    private void mockGetOfferPageTwoView(OfferPageTwo pageTwo) {
        when(offerPageTwoService.getOfferPageTwoViewByVisibleId(pageTwo))
                .thenReturn(OfferDataUtils.offerPageTwoView());
    }

    private void mockGetOfferPageTwoEntity(OfferPageTwo pageTwo) {
        when(offerPageTwoService.getOfferPageTwoByOfferVisibleId("id")).thenReturn(pageTwo);
    }

    private void mockSaveLocation() {
        when(locationService.saveLocation("name", "address"))
                .thenReturn(new LocationEntity());
    }


}
