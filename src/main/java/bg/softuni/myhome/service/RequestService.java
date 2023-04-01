package bg.softuni.myhome.service;

import bg.softuni.myhome.model.dto.AgencyRequestDTO;
import bg.softuni.myhome.model.dto.UserRequestDTO;
import bg.softuni.myhome.model.entities.RequestEntity;
import bg.softuni.myhome.model.enums.RequestStatusEnum;
import bg.softuni.myhome.model.view.RequestView;
import bg.softuni.myhome.repository.RequestRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class RequestService {

    private final RequestRepository requestRepository;
    private final OfferService offerService;
    private ModelMapper modelMapper;


    public RequestService(RequestRepository requestRepository, OfferService offerService, ModelMapper modelMapper) {
        this.requestRepository = requestRepository;
        this.offerService = offerService;
        this.modelMapper = modelMapper;
    }


    public void saveRequest(UserRequestDTO userRequestDTO, String visibleId) {
        RequestEntity request = new RequestEntity()
                .setClientName(userRequestDTO.getClientName())
                .setEmail(userRequestDTO.getEmail())
                .setMessage(userRequestDTO.getMessage())
                .setPhone(userRequestDTO.getPhone())
                .setReceivedOn(LocalDate.now())
                .setOffer(offerService.getOfferByVisibleId(visibleId))
                .setStatus(RequestStatusEnum.NEW);

        requestRepository.save(request);
    }

    public RequestView getRequestViewById(long id){
        return requestRepository.findById(id).map(this::toRequestView).orElse(null);
    }



    public List<RequestView> findRequestViewsByAgencyIdAndStatus(String userVisibleId, RequestStatusEnum requestStatus ){

        return requestRepository.findByOffer_Agency_User_VisibleIdAndStatus(userVisibleId, requestStatus)
                .stream()
                .map(this::toRequestView)
                .toList();
    }

    public Map<String, Integer> getRequestsCountForModel(String userVisibleId){
      Map<String, Integer> map = new HashMap<>();

      Arrays.stream(RequestStatusEnum.values())
              .forEach(requestStatusEnum -> map.put(requestStatusEnum.name().toLowerCase() +"RequestsCount",
                      findRequestViewsByAgencyIdAndStatus(userVisibleId, requestStatusEnum).size()));

        return map;
    }



    private RequestView toRequestView(RequestEntity request) {
       return modelMapper.map(request, RequestView.class);
    }

    public void editRequest(Long requestId, AgencyRequestDTO agencyRequestDTO) {
        Optional<RequestEntity> optRequest = requestRepository.findById(requestId);
        if(optRequest.isPresent()){
            RequestEntity request = optRequest.get();
            request.setStatus(agencyRequestDTO.getStatus())
                    .setNotes(agencyRequestDTO.getNotes());
            requestRepository.save(request);
        }
    }
}





