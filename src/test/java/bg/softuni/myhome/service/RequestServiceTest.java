package bg.softuni.myhome.service;

import bg.softuni.myhome.exception.ObjectNotFoundException;
import bg.softuni.myhome.model.dto.AgencyRequestDTO;
import bg.softuni.myhome.model.dto.UserRequestDTO;
import bg.softuni.myhome.model.entities.AgencyEntity;
import bg.softuni.myhome.model.entities.OfferEntity;
import bg.softuni.myhome.model.entities.RequestEntity;
import bg.softuni.myhome.model.entities.UserEntity;
import bg.softuni.myhome.model.enums.RequestStatusEnum;
import bg.softuni.myhome.model.view.RequestView;
import bg.softuni.myhome.repository.RequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {

    @Mock
    private RequestRepository mockRequestRepository;
    @Mock
    private OfferService mockOfferService;

    private final ModelMapper modelMapper = new ModelMapper();

    private RequestService testRequestService;
    @Captor
    private ArgumentCaptor<RequestEntity> requestEntityArgumentCaptor;


    @BeforeEach
    void setUp() {
        this.testRequestService = new RequestService(mockRequestRepository, mockOfferService,
                modelMapper);
    }

    @Test
    void test_saveRequest_SaveInvoked() {
        String offerVisibleId = "1114";
        RequestStatusEnum expectedStatus = RequestStatusEnum.NEW;
        UserRequestDTO dto = userRequestDTO();
        when(mockOfferService.getOfferById(offerVisibleId)).thenReturn(new OfferEntity());

        testRequestService.saveRequest(dto, offerVisibleId);

        verify(mockRequestRepository).save(requestEntityArgumentCaptor.capture());
        RequestEntity request = requestEntityArgumentCaptor.getValue();
        assertEquals(dto.getClientName(), request.getClientName());
        assertEquals(expectedStatus, request.getStatus());

    }

    @Test
    void test_getRequestViewById_RequestFound_ReturnsRequestView() {
        long id = 1L;
        String clientName = "testName";
        mockFindById();
        RequestView requestView = testRequestService.getRequestViewById(id);
        assertEquals(id, requestView.getId());
        assertEquals(clientName, requestView.getClientName());
    }

    @Test
    void test_getRequestViewById_RequestNotFound_Trows() {
        assertThrows(ObjectNotFoundException.class, () ->
                testRequestService.getRequestViewById(1L));

    }

    @Test
    void test_findRequestViewsByAgencyIdAndStatus_ReturnsListOfRequestView() {
        String userVisibleId = "testUser";
        RequestStatusEnum status = RequestStatusEnum.REJECT;
        RequestEntity request1 = new RequestEntity().setStatus(status);
        RequestEntity request2 = new RequestEntity().setStatus(status);
        setUserVisibleId(request1);
        setUserVisibleId(request2);

        when(mockRequestRepository.findByOffer_Agency_User_VisibleIdAndStatus(userVisibleId, status))
                .thenReturn(List.of(request1, request2));

        List<RequestView> requests =
                testRequestService.findRequestViewsByAgencyIdAndStatus(userVisibleId, status);

        assertEquals(2, requests.size());
        assertEquals(status, requests.get(0).getStatus());

    }

    @Test
    void test_editRequest_RequestFoundAndUpdated() {
        mockFindById();
        long requestId = 1L;
        AgencyRequestDTO dto = agencyRequestDTO();
        testRequestService.editRequest(requestId, dto);
        verify(mockRequestRepository, times(1)).save(requestEntityArgumentCaptor.capture());
        RequestEntity request = requestEntityArgumentCaptor.getValue();
        assertEquals(dto.getNotes(), request.getNotes());
        assertEquals(dto.getStatus(), request.getStatus());
        assertEquals(requestId, request.getId());

    }

    @Test
    void test_editRequest_RequestNotFoundAndNothingHappens() {
        verify(mockRequestRepository, never()).save(requestEntityArgumentCaptor.capture());

    }


    private UserRequestDTO userRequestDTO() {
        return new UserRequestDTO()
                .setClientName("testName")
                .setMessage("testMessage")
                .setEmail("testEmail")
                .setPhone("testPhone");
    }

    private AgencyRequestDTO agencyRequestDTO() {
        return new AgencyRequestDTO()
                .setStatus(RequestStatusEnum.REPLIED)
                .setNotes("testNotes");
    }

    private void setUserVisibleId(RequestEntity request) {
        request.setOffer(new OfferEntity().setAgency(new AgencyEntity().setUser(
                new UserEntity().setVisibleId("testUser"))));

    }

    private void mockFindById() {
        RequestEntity request = new RequestEntity().setId(1L).setClientName("testName");
        when(mockRequestRepository.findById(1L)).thenReturn(Optional.ofNullable(request));

    }

//    private RequestEntity requestEntity(){
//        return new RequestEntity()
//                .setId(1L).setClientName("testName")
//                .setStatus(RequestStatusEnum.NEW)
//                .setOffer(new OfferEntity())
//                .setEmail("testEmail")
//                .setClientName("testName")
//                .setMessage("testMessage")
//                .setEmail("testEmail")
//                .setPhone("testPhone");
//    }
}
