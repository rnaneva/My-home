package bg.softuni.myhome.web;

import bg.softuni.myhome.model.AppUserDetails;
import bg.softuni.myhome.service.*;
import com.cloudinary.Cloudinary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
public class HomeControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private Cloudinary cloudinary;
    @MockBean
    private CategoryService categoryService;
    @MockBean
    private CityService cityService;
    @MockBean
    private AgencyService agencyService;
    @MockBean
    private SearchService searchService;
    @MockBean
    private OfferService offerService;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private AppUserDetails appUserDetails;

    @Test
    void test_home() throws Exception {

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attributeExists("cities"))
                .andExpect(model().attributeExists("agencies"))
                .andExpect(model().attributeExists("lastAddedOffers"));

    }

    @Test
    void test_postAdvancedSearch_Fail() throws Exception {


        mockMvc.perform(post("/")
                        .param("categoryName", "category")
                        .param("cityName", "city")
                        .param("sortBy", "price")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/#advanced-search-title"));
    }




}
