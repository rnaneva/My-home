package bg.softuni.myhome.service;

import bg.softuni.myhome.exception.ObjectNotFoundException;
import bg.softuni.myhome.model.dto.SearchFormDTO;
import bg.softuni.myhome.model.entities.*;
import bg.softuni.myhome.model.enums.OfferTypeEnum;
import bg.softuni.myhome.model.enums.StatusEnum;
import bg.softuni.myhome.model.view.OfferAgencyView;
import bg.softuni.myhome.model.view.OfferDetailsView;
import bg.softuni.myhome.model.view.OfferView;
import bg.softuni.myhome.model.view.PictureView;
import bg.softuni.myhome.repository.OfferRepository;
import bg.softuni.myhome.util.EntitiesDataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OfferServiceTest {

    @Mock
    private OfferRepository mockOfferRepository;
    @Mock
    private AgencyService mockAgencyService;
    @Mock
    private UserService mockUserService;

    private final ModelMapper modelMapper = new ModelMapper();

    @Captor
    private ArgumentCaptor<OfferEntity> offerEntityArgumentCaptor;

    private OfferService testOfferService;

    @BeforeEach
    void setUp() {
        this.testOfferService = new OfferService(mockOfferRepository, mockAgencyService,
                modelMapper, mockUserService);
    }

    @Test
    void test_allRentProperties_returnsListOfOfferView() {
        OfferEntity offerForRent = EntitiesDataUtils.getOffer().setId(1L);
        offerForRent.getOfferPageOne().setType(OfferTypeEnum.RENT);

        when(mockOfferRepository.findByOfferPageOneType(OfferTypeEnum.RENT, StatusEnum.ACTIVE))
                .thenReturn(List.of(offerForRent));

        List<OfferView> offerViews = testOfferService.allRentProperties();
        OfferView offer = offerViews.get(0);


        assertEquals(1, offerViews.size());
        assertEquals(1, offer.getId());
        assertEquals(offerForRent.getVisibleId(), offer.getVisibleId());

    }

    @Test
    void test_allSaleProperties_returnsListOfOfferView() {
        OfferEntity offerForSale = EntitiesDataUtils.getOffer().setId(2L);

        when(mockOfferRepository.findByOfferPageOneType(OfferTypeEnum.SALE, StatusEnum.ACTIVE))
                .thenReturn(List.of(offerForSale));

        List<OfferView> offerViews = testOfferService.allSaleProperties();
        OfferView offer = offerViews.get(0);

        assertEquals(1, offerViews.size());
        assertEquals(2, offer.getId());
        assertEquals(offerForSale.getVisibleId(), offer.getVisibleId());
    }


    @Test
    void test_findOffersBySearchForm_returnsListOfOfferView() {
        SearchFormDTO dto = getSearchFormDTO();
        OfferEntity offer1 = EntitiesDataUtils.getOffer().setId(1L);
        offer1.getOfferPageOne().setType(OfferTypeEnum.RENT);
        OfferEntity offer2 = EntitiesDataUtils.getOffer().setId(2L);
        offer2.getOfferPageOne().setType(OfferTypeEnum.RENT);


        when(mockOfferRepository.findOffersBySearchForm(OfferTypeEnum.RENT, "testCategory",
                "testCity", null, null, BigDecimal.valueOf(4000), null, null
        ))
                .thenReturn(List.of(offer1, offer2));

        List<OfferView> offers = testOfferService.findOffersBySearchForm(dto);

        assertEquals(2, offers.size());
        assertEquals(1, offers.get(0).getId());


    }

    @Test
    void test_getOffersAgencyViewByStatus_returnsListOfAgencyView() {
        String userVisibleId = "testUserId";
        StatusEnum status = StatusEnum.ACTIVE;
        OfferEntity offer1 = EntitiesDataUtils.getOffer();
        OfferEntity offer2 = EntitiesDataUtils.getOffer();


        when(mockOfferRepository.findByAgency_User_VisibleIdAndStatus(userVisibleId, status))
                .thenReturn(List.of(offer1, offer2));

        List<OfferAgencyView> offers =
                testOfferService.getOffersAgencyViewByStatus(userVisibleId, status);

        assertEquals(2, offers.size());
        assertEquals("testOfferId", offers.get(0).getVisibleId());
        assertEquals(StatusEnum.ACTIVE, offers.get(0).getStatus());

    }

    @Test
    void test_findDetailedOfferByVisibleId_findsOfferAndReturnsDetailedOffersView() {
        OfferEntity offer = EntitiesDataUtils.getOffer();
        String visibleId = "testOfferId";
        when(mockOfferRepository.findOfferByVisibleId("testOfferId"))
                .thenReturn(Optional.ofNullable(offer));

        OfferDetailsView offerDV =
                testOfferService.findDetailedOfferByVisibleId(visibleId);

        assertNotNull(offerDV);
        assertEquals(visibleId, offerDV.getVisibleId());
        assertEquals(offer.getOfferPageTwo().getConstructionYear(),
                offerDV.getConstructionYear());
    }

    @Test
    void test_findDetailedOfferByVisibleId_notFoundShouldThrow() {

        assertThrows(ObjectNotFoundException.class,
                () -> testOfferService.findDetailedOfferByVisibleId("not_found"));
    }


    @Test
    void test_createOfferWithPageOne_assignToOfferEntity() {
        OfferPageOne pageOne = EntitiesDataUtils.getTestOfferPageOne();
        String userVisibleId = "testUserId";
        StatusEnum expectedStatus = StatusEnum.INACTIVE;

        when(mockAgencyService.findAgencyByUserVisibleId(userVisibleId))
                .thenReturn(EntitiesDataUtils.addAgency());
        testOfferService.createOfferWithPageOne(pageOne, userVisibleId);
        verify(mockOfferRepository, times(1)).save(offerEntityArgumentCaptor.capture());
        OfferEntity createdOffer = offerEntityArgumentCaptor.getValue();
        pageOne.setDescription("");
        assertEquals("1113", createdOffer.getVisibleId());
        assertEquals(expectedStatus, createdOffer.getStatus());
    }


    @Test
    void test_addPageTwoToOffer_assignToOfferEntity() {
        OfferPageTwo pageTwo = EntitiesDataUtils.getTestOfferPageTwo();

        String offerVisibleId = "1113";
        OfferEntity offer = new OfferEntity().setVisibleId(offerVisibleId);

        mockFindOfferByVisibleId(offerVisibleId, offer);

        testOfferService.addPageTwoToOffer(pageTwo, offerVisibleId);

        verify(mockOfferRepository, times(1)).save(offerEntityArgumentCaptor.capture());
        OfferEntity offerWithPageTwo = offerEntityArgumentCaptor.getValue();
        assertEquals(pageTwo.getConstructionYear(), offerWithPageTwo.getOfferPageTwo().getConstructionYear());
        assertEquals(pageTwo.getLocation(), offer.getOfferPageTwo().getLocation());

    }




    @Test
    void test_changeOfferStatus_statusUpdated(){
        OfferEntity offer = EntitiesDataUtils.getOffer();
        StatusEnum newStatus = StatusEnum.INACTIVE;

        testOfferService.changeOfferStatus(offer, newStatus);
        verify(mockOfferRepository, times(1)).save(offerEntityArgumentCaptor.capture());
        OfferEntity offerUpdated = offerEntityArgumentCaptor.getValue();
        assertEquals(newStatus, offerUpdated.getStatus());
    }

    @Test
    void test_getOfferPicturesByVisibleId_returnsListOfPictureView(){
        String offerVisibleId = "1113";
        OfferEntity offer = new OfferEntity().setVisibleId(offerVisibleId);
        mockFindOfferByVisibleId(offerVisibleId, offer);
        offer.setPictures(EntitiesDataUtils.addPictures());
        List<PictureView> pics = testOfferService.getOfferPicturesByVisibleId(offerVisibleId);
        assertEquals(3, pics.size());
        assertEquals("url1", pics.get(0).getUrl());
        assertEquals("title2",pics.get(1).getName());
        assertEquals(3L, pics.get(2).getId());

    }


    private void mockFindOfferByVisibleId(String offerVisibleId, OfferEntity offer) {
        when(mockOfferRepository.findOfferByVisibleId(offerVisibleId))
                .thenReturn(Optional.ofNullable(offer));
        assert offer != null;
        when(mockOfferRepository.getOfferByVisibleIdWithoutPics(offerVisibleId))
                .thenReturn(Optional.of(offer));
    }





    private SearchFormDTO getSearchFormDTO() {
        return new SearchFormDTO()
                .setType(OfferTypeEnum.RENT)
                .setCategoryName("testCategory")
                .setCityName("testCity")
                .setMaxPrice(BigDecimal.valueOf(4000))
                .setSortBy("price");
    }



}
