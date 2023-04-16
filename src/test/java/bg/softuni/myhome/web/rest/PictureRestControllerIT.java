package bg.softuni.myhome.web.rest;

import bg.softuni.myhome.model.view.PictureView;
import bg.softuni.myhome.service.OfferService;
import bg.softuni.myhome.service.PictureService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static bg.softuni.myhome.commons.StaticVariables.ROLE_MODERATOR;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PictureRestControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OfferService offerService;

    @MockBean
    private PictureService pictureService;


    @MockBean
    private Cloudinary cloudinary;


    @Test
    @WithMockUser(username = "testUsername", authorities = {ROLE_MODERATOR})
    void test_getOfferImages_ReturnedAndResponseStatusOK() throws Exception {

        PictureView pic1 = pictureView();
        PictureView pic2 = pictureView().setUrl("url2");
        PictureView pic3 = pictureView().setUrl("url3");

        when(offerService.getOfferPicturesByVisibleId("testOfferId"))
                .thenReturn(List.of(pic1, pic2, pic3));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/offers/testOfferId/pictures"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.[0].url", is("url1")))
                .andExpect(jsonPath("$.[1].url", is("url2")))
                .andExpect(jsonPath("$.[2].url", is("url3")));
    }

    @Test
    @WithMockUser(username = "testUsername", authorities = {ROLE_MODERATOR})
    void test_getImage_ReturnedAndResponseStatusOK() throws Exception {

        PictureView pic1 = pictureView();

        when(pictureService.getPictureViewById(1)).thenReturn(pic1);


        mockMvc.perform(MockMvcRequestBuilders.get("/api/offers/testOfferId/pictures/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url", is("url1")))
                .andExpect(jsonPath("$.name", is("name1")));

    }


    private PictureView pictureView(){
        return new PictureView().setName("name1")
                .setUrl("url1")
                .setId(1L);
    }


}
