package bg.softuni.myhome.web.rest;

import bg.softuni.myhome.model.entities.OfferEntity;
import bg.softuni.myhome.model.entities.RequestEntity;
import bg.softuni.myhome.model.enums.RequestStatusEnum;
import bg.softuni.myhome.model.enums.StatusEnum;
import bg.softuni.myhome.model.view.OfferAgencyView;
import bg.softuni.myhome.repository.RequestRepository;
import bg.softuni.myhome.service.OfferService;
import bg.softuni.myhome.util.EntitiesDataUtils;
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
public class AgencyRestControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OfferService offerService;

    @MockBean
    private Cloudinary cloudinary;


    @MockBean
    private RequestRepository requestRepository;


    @Test
    @WithMockUser(username = "testUsername", authorities = {ROLE_MODERATOR})
    void test_getActiveOffers_OffersFound_ReturnedAndResponseStatusOK() throws Exception {


        OfferAgencyView offer1 = offerAgencyView().setVisibleId("offer1");
        OfferAgencyView offer2 = offerAgencyView().setVisibleId("offer2");
        mockGetOffersByStatus(StatusEnum.ACTIVE, List.of(offer1, offer2));


        mockMvc.perform(MockMvcRequestBuilders.get("/api/offers/active/testVisibleId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.[0].visibleId", is("offer1")))
                .andExpect(jsonPath("$.[1].visibleId", is("offer2")));
    }


    @Test
    @WithMockUser(username = "testUsername", authorities = {ROLE_MODERATOR})
    void test_getInactiveOffers_OffersFound_ReturnedAndResponseStatusOK() throws Exception {


        OfferAgencyView offer3 = offerAgencyView().setVisibleId("offer3")
                .setStatus(StatusEnum.INACTIVE);

        mockGetOffersByStatus(StatusEnum.INACTIVE, List.of(offer3));


        mockMvc.perform(MockMvcRequestBuilders.get("/api/offers/inactive/testVisibleId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.[0].offerPageOneName", is("testName2")))
                .andExpect(jsonPath("$.[0].status", is("INACTIVE")))
                .andExpect(jsonPath("$.[0].visibleId", is("offer3")));
    }

    @Test
    @WithMockUser(username = "testUsername", authorities = {ROLE_MODERATOR})
    void test_getRepliedRequests_RequestsFound_StatusOK() throws Exception {

        RequestEntity request1 = EntitiesDataUtils.addRequest()
                .setStatus(RequestStatusEnum.REPLIED).setPhone("phone1").setId(1L);
        RequestEntity request2 = EntitiesDataUtils.addRequest()
                .setStatus(RequestStatusEnum.REPLIED).setPhone("phone2").setId(2L);

        mockGetRequestsByStatus(RequestStatusEnum.REPLIED, List.of(request1, request2));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/requests/replied/testVisibleId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.[0].clientName", is("TestName")))
                .andExpect(jsonPath("$.[0].phone", is("phone1")))
                .andExpect(jsonPath("$.[1].phone", is("phone2")));

    }


    @Test
    @WithMockUser(username = "testUsername", authorities = {ROLE_MODERATOR})
    void test_getNewRequests_RequestsFound_StatusOK() throws Exception {

        RequestEntity request1 = EntitiesDataUtils.addRequest()
                .setStatus(RequestStatusEnum.NEW).setClientName("client1").setId(1L);
        RequestEntity request2 = EntitiesDataUtils.addRequest()
                .setStatus(RequestStatusEnum.NEW).setClientName("client2").setId(2L);

        mockGetRequestsByStatus(RequestStatusEnum.NEW, List.of(request1, request2));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/requests/new/testVisibleId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.[0].clientName", is("client1")))
                .andExpect(jsonPath("$.[1].clientName", is("client2")));

    }

    @Test
    @WithMockUser(username = "testUsername", authorities = {ROLE_MODERATOR})
    void test_getRejectedRequests_RequestsFound_StatusOK() throws Exception {

        RequestEntity request1 = EntitiesDataUtils.addRequest()
                .setStatus(RequestStatusEnum.REJECT).setClientName("client1").setId(1L);


        mockGetRequestsByStatus(RequestStatusEnum.REJECT, List.of(request1));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/requests/rejected/testVisibleId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.[0].clientName", is("client1")));

    }

    @Test
    @WithMockUser(username = "testUsername", authorities = {ROLE_MODERATOR})
    void test_getInspectionRequests_RequestsFound_StatusOK() throws Exception {

        RequestEntity request1 = EntitiesDataUtils.addRequest()
                .setStatus(RequestStatusEnum.INSPECTION).setNotes("note1").setId(1L);
        RequestEntity request2 = EntitiesDataUtils.addRequest()
                .setStatus(RequestStatusEnum.INSPECTION).setNotes("note2").setId(2L);
        RequestEntity request3 = EntitiesDataUtils.addRequest()
                .setStatus(RequestStatusEnum.INSPECTION).setNotes("note3").setId(3L);

        mockGetRequestsByStatus(RequestStatusEnum.INSPECTION, List.of(request1, request2, request3));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/requests/inspection/testVisibleId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.[0].notes", is("note1")))
                .andExpect(jsonPath("$.[1].notes", is("note2")))
                .andExpect(jsonPath("$.[2].notes", is("note3")));

    }

    @Test
    @WithMockUser(username = "testUsername", authorities = {ROLE_MODERATOR})
    void test_activateOffer_HTTPStatusOK() throws Exception {


        OfferEntity offer1 = EntitiesDataUtils.getOffer().setId(1L).setVisibleId("offer1");

        mockGetOffer(offer1);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/offers/inactive/offer1/activate"))
                .andExpect(status().isOk());

    }


    @Test
    @WithMockUser(username = "testUsername", authorities = {ROLE_MODERATOR})
    void test_deactivateOffer_HTTPStatusOK() throws Exception {


        OfferEntity offer1 = EntitiesDataUtils.getOffer().setId(1L).setVisibleId("offer1");

        mockGetOffer(offer1);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/offers/active/offer1/deactivate"))
                .andExpect(status().isOk());

    }

    private OfferAgencyView offerAgencyView() {
        return new OfferAgencyView()
                .setCreatedOn("01-01-2023")
                .setStatus(StatusEnum.ACTIVE)
                .setOfferPageOneName("testName2")
                .setVisibleId("visibleId1");

    }

    private void mockGetOffersByStatus(StatusEnum status, List<OfferAgencyView> offers) {
        when(offerService.getOffersAgencyViewByStatus("testVisibleId",
                status)).thenReturn(offers);
    }

    private void mockGetRequestsByStatus(RequestStatusEnum status, List<RequestEntity> requests) {
        when(requestRepository.findByOffer_Agency_User_VisibleIdAndStatus("testVisibleId", status))
                .thenReturn(requests);
    }

    private void mockGetOffer(OfferEntity offer1) {
        when(offerService.getOfferById("offer1")).thenReturn(offer1);
    }


}
