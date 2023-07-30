package bg.softuni.myhome.service;

import bg.softuni.myhome.exception.ObjectNotFoundException;
import bg.softuni.myhome.model.dto.AgencyCreateProfileDTO;
import bg.softuni.myhome.model.dto.AgencyEditProfileDTO;
import bg.softuni.myhome.model.entities.AgencyEntity;
import bg.softuni.myhome.repository.AgencyRepository;


import bg.softuni.myhome.util.EntitiesDataUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public class AgencyServiceTest {



    @Mock
    private AgencyRepository mockAgencyRepository;

    @Mock
    private UserService mockUserService;


    @Mock
    private CloudinaryService mockCloudinaryService;

    @Captor
    private ArgumentCaptor<AgencyEntity> agencyEntityArgumentCaptor;

    private AgencyService testAgencyService;

    @BeforeEach
    void setUp() {
        testAgencyService = new AgencyService(
                mockAgencyRepository, mockUserService, mockCloudinaryService);
    }


    @Test
    void test_agencyCreation_saveInvoked() throws IOException {

        AgencyCreateProfileDTO dto = new AgencyCreateProfileDTO()
                .setAddress("Sofia, 1000")
                .setName("Pink Agency")
                .setPhoneNumber("0899999999");


        testAgencyService.createAgencyProfile("123-c-2344-d", dto);
        verify(mockAgencyRepository).save(agencyEntityArgumentCaptor.capture());
        AgencyEntity actualSaved = agencyEntityArgumentCaptor.getValue();
        assertEquals("Sofia, 1000", actualSaved.getAddress());
        assertEquals("Pink Agency", actualSaved.getName());
        assertEquals("0899999999", actualSaved.getPhoneNumber());
    }

    @Test
    void test_findAgencyByUserId_ThrowsForNonExistingId() {
        Assertions.assertThrows(ObjectNotFoundException.class,
                () -> testAgencyService.findAgencyByUserId(1));

    }

    @Test
    void test_FindAgencyByUserVisibleId_ThrowsForNonExistingId() {
        Assertions.assertThrows(ObjectNotFoundException.class,
                () -> testAgencyService.findAgencyByUserVisibleId("123-c-2344-d"));

    }


    @Test
    void test_EditAgencyProfile_SuccessfulEditOnCurrentProfile() throws IOException {

        AgencyEditProfileDTO dto = new AgencyEditProfileDTO().setAddress("Sofia, 1000")
                .setPhoneNumber("0899999999")
                .setLogo(EntitiesDataUtils.createMultipartFile());

       AgencyEntity agency = new AgencyEntity().setName("old")
                .setAddress("null")
                .setPhoneNumber("000");

        testAgencyService.editAgencyProfile(agency, dto);

        verify(mockAgencyRepository).saveAndFlush(agencyEntityArgumentCaptor.capture());
        AgencyEntity actualEdited = agencyEntityArgumentCaptor.getValue();
        assertEquals(actualEdited.getAddress(), "Sofia, 1000");
        assertEquals(actualEdited.getName(), "old");
        assertEquals(actualEdited.getPhoneNumber(), "0899999999");
    }



}
