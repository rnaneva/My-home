package bg.softuni.myhome.service;

import bg.softuni.myhome.model.dto.SearchFormDTO;
import bg.softuni.myhome.model.entities.*;
import bg.softuni.myhome.model.enums.OfferTypeEnum;
import bg.softuni.myhome.model.enums.StatusEnum;
import bg.softuni.myhome.model.view.OfferAgencyView;
import bg.softuni.myhome.model.view.OfferDetailsView;
import bg.softuni.myhome.model.view.OfferView;
import bg.softuni.myhome.repository.OfferRepository;
import bg.softuni.myhome.util.TestDataUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OfferServiceTest {

    @Mock
    private OfferRepository mockOfferRepository;
    @Mock
    private AgencyService mockAgencyService;
    private ModelMapper modelMapper = new ModelMapper();

    @Captor
    private ArgumentCaptor<OfferEntity> offerEntityArgumentCaptor;

    private OfferService testOfferService;

    @BeforeEach
    void setUp() {
        this.testOfferService = new OfferService(mockOfferRepository, mockAgencyService,
                modelMapper);
    }

    @Test
    void test_allRentProperties_returnsListOfOfferView() {
        OfferEntity offerForRent = getOffer().setId(1L);
        offerForRent.getOfferPageOne().setType(OfferTypeEnum.RENT);
        OfferEntity offerForSale = getOffer().setId(2L);

        when(mockOfferRepository.findByOfferPageOneType(OfferTypeEnum.RENT, StatusEnum.ACTIVE))
                .thenReturn(List.of(offerForRent));

        List<OfferView> offerViews = testOfferService.allRentProperties();
        OfferView offer = offerViews.get(0);


        assertEquals(1, offerViews.size());
        assertEquals(1, offer.getId() );
        assertEquals(offerForRent.getVisibleId(), offer.getVisibleId());

    }

    @Test
    void test_allSaleProperties_returnsListOfOfferView(){
        OfferEntity offerForRent = getOffer().setId(1L);
        offerForRent.getOfferPageOne().setType(OfferTypeEnum.RENT);
        OfferEntity offerForSale = getOffer().setId(2L);

        when(mockOfferRepository.findByOfferPageOneType(OfferTypeEnum.SALE, StatusEnum.ACTIVE))
                .thenReturn(List.of(offerForSale));

        List<OfferView> offerViews = testOfferService.allSaleProperties();
        OfferView offer = offerViews.get(0);


        assertEquals(1, offerViews.size());
        assertEquals(2, offer.getId() );
        assertEquals(offerForSale.getVisibleId(), offer.getVisibleId());
    }


    @Test
    void test_findOffersBySearchForm_returnsListOfOfferView(){
        SearchFormDTO dto = getSearchFormDTO();
        OfferEntity offerForRent = getOffer().setId(1L).setStatus(StatusEnum.ACTIVE);
        offerForRent.getOfferPageOne().setType(OfferTypeEnum.RENT);
        OfferEntity offerForSale = getOffer().setId(2L).setStatus(StatusEnum.INACTIVE);


        when(mockOfferRepository.findOffersBySearchForm(OfferTypeEnum.RENT, "testCategory",
                "testCity", null, null, BigDecimal.valueOf(4000),null,null))
                .thenReturn(List.of(offerForRent));

        List<OfferView> offers = testOfferService.findOffersBySearchForm(dto);

        assertEquals(1, offers.size());
        assertEquals(1, offers.get(0).getId());

    }

    void test_getOffersAgencyViewByStatus_returnsListOfAgencyView(){
        OfferEntity offer1 = new OfferEntity().setStatus(StatusEnum.ACTIVE).setAgency(getAgency());
        OfferEntity offer2 = new OfferEntity().setStatus(StatusEnum.ACTIVE).setAgency(getAgency());
        OfferEntity offer3 = new OfferEntity().setStatus(StatusEnum.INACTIVE).setAgency(getAgency());
        OfferEntity offer4 = new OfferEntity().setStatus(StatusEnum.ACTIVE).setAgency(null);
        mockOfferRepository.save(offer3);
        mockOfferRepository.save(offer4);
        String userVisibleId = "testId";
        StatusEnum status = StatusEnum.ACTIVE;
        when(mockOfferRepository.findByAgency_User_VisibleIdAndStatus(userVisibleId, status))
                .thenReturn(List.of(offer1, offer2));
        List<OfferAgencyView> agencyViews = testOfferService.getOffersAgencyViewByStatus(userVisibleId, status);


    }

    private OfferEntity getOffer() {
        return new OfferEntity()
                .setCreatedOn(LocalDate.now())
                .setVisibleId("testVisibleId")
                .setPictures(
                        List.of(new PictureEntity().setUrl("test1"),
                                new PictureEntity().setUrl("test2")))
                .setAgency(new AgencyEntity().setLogoUrl("testLogoUrl"))
                .setOfferPageTwo(TestDataUtils.getTestOfferPageTwo())
                .setOfferPageOne(TestDataUtils.getTestOfferPageOne());
    }


    private AgencyEntity getAgency(){
      return new AgencyEntity().setUser(new UserEntity().setVisibleId("testId"));
    }

    private SearchFormDTO getSearchFormDTO(){
        return new SearchFormDTO()
                .setType(OfferTypeEnum.RENT)
                .setCategoryName("testCategory")
                .setCityName("testCity")
                .setMaxPrice(BigDecimal.valueOf(4000));
    }


}
