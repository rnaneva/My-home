package bg.softuni.myhome.web;

import bg.softuni.myhome.model.dto.SearchFormDTO;
import bg.softuni.myhome.model.enums.OfferTypeEnum;
import bg.softuni.myhome.model.view.OfferView;
import bg.softuni.myhome.service.OfferService;
import bg.softuni.myhome.service.SearchService;
import com.cloudinary.Cloudinary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SearchControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchService searchService;

    @MockBean
    private OfferService offerService;

    @MockBean
    private Cloudinary cloudinary;

    @Test
    void test_searchResult() throws Exception {
        OfferView offer1 = offerView();
        OfferView offer2 = offerView().setId(2L);
        SearchFormDTO dto = searchFormDTO();
        mockFindSearch(dto);
        mockFindOffers(offer1, offer2, dto);

        mockMvc.perform(get("/search/{visibleId}", "id"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("search_id"))
            .andExpect(model().attributeExists("offers"))
            .andExpect(view().name("search-form-result"));

    }


    @Test
    void test_subscribeUser() throws Exception {
        mockMvc.perform(post("/search/{visibleId}", "id")
                        .param("email", "email@abv.bg")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("successful-message"));
    }

    @Test
    void test_subscribeUser_fails() throws Exception {
        mockMvc.perform(post("/search/{visibleId}", "id")
                        .param("email", "")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/search/id"));
    }



    private SearchFormDTO searchFormDTO(){
        return  new SearchFormDTO()
                .setType(OfferTypeEnum.SALE)
                .setCategoryName("studio")
                .setCityName("Varna");
    }

    private OfferView offerView() {

        return new OfferView()
                .setName("name")
                .setImageURL("url")
                .setArea(BigDecimal.ONE)
                .setFloorInfo("floorInfo")
                .setPlan("plan")
                .setDescription("decription")
                .setAddress("fullAddress")
                .setPrice(BigDecimal.TEN)
                .setCity("cityName")
                .setVisibleId("id")
                .setCreatedOn(LocalDate.now())
                .setId(1L);
    }

    private void mockFindOffers(OfferView offer1, OfferView offer2, SearchFormDTO dto) {
        when(offerService.findOffersBySearchForm(dto)).thenReturn(List.of(offer1, offer2));
    }

    private void mockFindSearch(SearchFormDTO dto) {
        when(searchService.findSearchByVisibleId("id")).thenReturn(dto);
    }

}
