package bg.softuni.myhome.service;

import bg.softuni.myhome.model.entities.CityEntity;
import bg.softuni.myhome.model.entities.LocationEntity;
import bg.softuni.myhome.repository.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {



    @Mock
    private CityService mockCityService;

    @Mock
    private LocationRepository mockLocationRepository;

    @Captor
    private ArgumentCaptor<LocationEntity> locationEntityArgumentCaptor;

    private LocationService testLocationService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void test_SaveLocation_SaveInvoked() {

        String cityName = "testCity";
        String address = "testAddress";

        this.testLocationService = new LocationService(mockLocationRepository, mockCityService);

        when(mockCityService.findByName("testCity"))
                .thenReturn(new CityEntity().setName("testCity"));

        testLocationService.saveLocation(cityName, address);
        verify(mockLocationRepository).save(locationEntityArgumentCaptor.capture());
        LocationEntity savedLocation = locationEntityArgumentCaptor.getValue();

        assertEquals(cityName, savedLocation.getCity().getName());
        assertEquals(address, savedLocation.getAddress());

    }



}
