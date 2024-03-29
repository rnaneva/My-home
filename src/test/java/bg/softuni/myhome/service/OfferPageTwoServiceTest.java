package bg.softuni.myhome.service;

import bg.softuni.myhome.model.dto.OfferPageTwoDTO;
import bg.softuni.myhome.model.entities.OfferPageTwo;
import bg.softuni.myhome.model.enums.AvailableEnum;
import bg.softuni.myhome.repository.OfferPageTwoRepository;
import bg.softuni.myhome.util.EntitiesDataUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class OfferPageTwoServiceTest {

    @Mock
    private OfferPageTwoRepository mockOfferPageTwoRepository;
    private ModelMapper modelMapper = new ModelMapper();

    @Mock
    private OfferService mockOfferService;

    @Mock
    private LocationService mockLocationService;

    @Mock
    private CityService mockCityService;

    @Captor
    private ArgumentCaptor<OfferPageTwo> offerPageTwoArgumentCaptor;
    private OfferPageTwoService testOfferPageTwoService;

    @BeforeEach
    void setUp() {
        this.testOfferPageTwoService = new OfferPageTwoService(mockOfferPageTwoRepository,
                modelMapper, mockOfferService, mockLocationService, mockCityService);
    }

    @Test
    void test_savePageTwo_savedWithCorrectData() {
        OfferPageTwoDTO dto = getTestDTO();
        mockLocation();
        testOfferPageTwoService.savePageTwo(dto);
        verify(mockOfferPageTwoRepository).save(offerPageTwoArgumentCaptor.capture());
        OfferPageTwo actualSaved = offerPageTwoArgumentCaptor.getValue();
        Assertions.assertEquals(dto.getAddress(), actualSaved.getLocation().getAddress());
        Assertions.assertEquals(dto.getCityName(), actualSaved.getLocation().getCity().getName());
        Assertions.assertEquals(dto.getBathrooms(), actualSaved.getBathrooms());
        Assertions.assertEquals(dto.getBalconies(), actualSaved.getBalconies());
        Assertions.assertEquals(dto.getBedrooms(), actualSaved.getBedrooms());
        Assertions.assertEquals(dto.getFloor(), actualSaved.getFloor());
        Assertions.assertEquals(dto.getAllFloors(), actualSaved.getAllFloors());
        Assertions.assertEquals(dto.getElevator(), actualSaved.getElevator());
        Assertions.assertEquals(dto.getConstructionYear(), actualSaved.getConstructionYear());
        Assertions.assertEquals(dto.getParking(), actualSaved.getParking());

    }

    @Test
    void test_editPageTwo_updatesCurrentOfferPageTwo() {
        OfferPageTwoDTO dto = getTestDTO();
        OfferPageTwo pageToEdit = EntitiesDataUtils.getTestOfferPageTwo();
        mockLocation();
        testOfferPageTwoService.editPageTwo(pageToEdit, dto);
        verify(mockOfferPageTwoRepository).save(offerPageTwoArgumentCaptor.capture());
        OfferPageTwo actualEditedPageTwo = offerPageTwoArgumentCaptor.getValue();
        Assertions.assertEquals(3L, actualEditedPageTwo.getId());
        Assertions.assertEquals(dto.getBalconies(), actualEditedPageTwo.getBalconies());
        Assertions.assertNotEquals(1, actualEditedPageTwo.getBathrooms());

    }

    private void mockLocation() {
        when(mockLocationService.saveLocation("TestCity", "testAddress"))
                .thenReturn(EntitiesDataUtils.newLocation());
    }

    private OfferPageTwoDTO getTestDTO() {
        return new OfferPageTwoDTO()
                .setBalconies(2)
                .setAddress("testAddress")
                .setBathrooms(2)
                .setBedrooms(2)
                .setAllFloors(10)
                .setFloor(1)
                .setCityName("TestCity")
                .setConstructionYear(1999)
                .setElevator(AvailableEnum.YES)
                .setParking(AvailableEnum.NO);
    }


}
