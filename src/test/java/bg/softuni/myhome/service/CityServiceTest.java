package bg.softuni.myhome.service;

import bg.softuni.myhome.model.dto.CityDTO;
import bg.softuni.myhome.model.entities.CityEntity;
import bg.softuni.myhome.repository.CityRepository;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CityServiceTest {

    @Mock
    private CityRepository mockCityRepository;

    private CityService testCityService;

    @Captor
    private ArgumentCaptor<CityEntity> cityServiceArgumentCaptor;

    @BeforeEach
    void setUp() {
        this.testCityService = new CityService(mockCityRepository);
    }

    @Test
    void test_SaveCity_saveInvoked() {

        String expectedCity = "testCity";

        CityDTO cityDTO = new CityDTO().setName(expectedCity);

        testCityService.saveCity(cityDTO);
        verify(mockCityRepository).save(cityServiceArgumentCaptor.capture());
        String actualName = cityServiceArgumentCaptor.getValue().getName();
        assertEquals(expectedCity, actualName);
    }

}
