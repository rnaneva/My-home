package bg.softuni.myhome.service;

import bg.softuni.myhome.exception.ObjectNotFoundException;
import bg.softuni.myhome.model.dto.AgencyCreateProfileDTO;
import bg.softuni.myhome.model.dto.AgencyEditProfileDTO;
import bg.softuni.myhome.model.entities.AgencyEntity;
import bg.softuni.myhome.repository.AgencyRepository;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

import java.io.FileNotFoundException;
import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public class AgencyServiceTest {

    private final String EXPECTED_USER_VISIBLE_ID = "123-c-2344-d";
    private final String EXPECTED_PHONE_NUMBER = "0899999999";
    private final String EXPECTED_ADDRESS = "Sofia, 1000";

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
        String EXPECTED_AGENCY_NAME = "Pink Agency";

        AgencyCreateProfileDTO dto = new AgencyCreateProfileDTO()
                .setAddress(EXPECTED_ADDRESS)
                .setName(EXPECTED_AGENCY_NAME)
                .setPhoneNumber(EXPECTED_PHONE_NUMBER);


        testAgencyService.createAgencyProfile(EXPECTED_USER_VISIBLE_ID, dto);
        verify(mockAgencyRepository).save(agencyEntityArgumentCaptor.capture());
        AgencyEntity actualSaved = agencyEntityArgumentCaptor.getValue();
        assertEquals(EXPECTED_ADDRESS, actualSaved.getAddress());
        assertEquals(EXPECTED_AGENCY_NAME, actualSaved.getName());
        assertEquals(EXPECTED_PHONE_NUMBER, actualSaved.getPhoneNumber());
    }

    @Test
    void test_findAgencyByUserId_ThrowsForNonExistingId() {
        Assertions.assertThrows(ObjectNotFoundException.class,
                () -> testAgencyService.findAgencyByUserId(1));

    }

    @Test
    void test_FindAgencyByUserVisibleId_ThrowsForNonExistingId() {
        Assertions.assertThrows(ObjectNotFoundException.class,
                () -> testAgencyService.findAgencyByUserVisibleId(EXPECTED_USER_VISIBLE_ID));

    }


    @Test
    void test_EditAgencyProfile_SuccessfulEditOnCurrentProfile() throws IOException {

        AgencyEditProfileDTO dto = new AgencyEditProfileDTO().setAddress(EXPECTED_ADDRESS)
                .setPhoneNumber(EXPECTED_PHONE_NUMBER)
                .setLogo(createMultipartFile());

       AgencyEntity agency = new AgencyEntity().setName("old")
                .setAddress("null")
                .setPhoneNumber("000");

        testAgencyService.editAgencyProfile(agency, dto);

        verify(mockAgencyRepository).saveAndFlush(agencyEntityArgumentCaptor.capture());
        AgencyEntity actualEdited = agencyEntityArgumentCaptor.getValue();
        assertEquals(actualEdited.getAddress(), EXPECTED_ADDRESS);
        assertEquals(actualEdited.getName(), "old");
        assertEquals(actualEdited.getPhoneNumber(), EXPECTED_PHONE_NUMBER);
    }

    private static MultipartFile createMultipartFile() throws FileNotFoundException {
        byte[] image = "src/test/resources/image/Logo.png".getBytes();
        return new MockMultipartFile("file", image);
    }


}
