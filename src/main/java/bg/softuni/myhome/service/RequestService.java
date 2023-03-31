package bg.softuni.myhome.service;

import bg.softuni.myhome.model.dto.RequestDTO;
import bg.softuni.myhome.model.entities.RequestEntity;
import bg.softuni.myhome.model.enums.RequestStatusEnum;
import bg.softuni.myhome.model.enums.StatusEnum;
import bg.softuni.myhome.model.view.RequestView;
import bg.softuni.myhome.repository.RequestRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    public void saveRequest(RequestDTO requestDTO, String visibleId) {
        RequestEntity request = new RequestEntity()
                .setClientName(requestDTO.getClientName())
                .setEmail(requestDTO.getEmail())
                .setMessage(requestDTO.getMessage())
                .setPhone(requestDTO.getPhone())
                .setDate(LocalDate.now())
                .setOffer(offerService.getOfferByVisibleId(visibleId))
                .setStatus(RequestStatusEnum.NEW);

        requestRepository.save(request);
    }

    public List<RequestView> findRequestViewsByAgencyIdAndStatus(long agencyId, RequestStatusEnum requestStatus ){

        return requestRepository.findByOffer_Agency_Id(agencyId)
                .stream()
                .filter(r-> r.getStatus().equals(requestStatus))
                .map(this::toRequestView)
                .toList();
    }

    public Map<String, Integer> getRequestsCountForModel(long agencyId){
      Map<String, Integer> map = new HashMap<>();

      int newRequestsCount =
                findRequestViewsByAgencyIdAndStatus(agencyId, RequestStatusEnum.NEW).size();
        int repliedRequestsCount =
                findRequestViewsByAgencyIdAndStatus(agencyId, RequestStatusEnum.REPLIED).size();
        int forInspectionRequestsCount =
                findRequestViewsByAgencyIdAndStatus(agencyId, RequestStatusEnum.INSPECTION).size();
        int rejectedRequestsCount =
                findRequestViewsByAgencyIdAndStatus(agencyId, RequestStatusEnum.REJECT).size();

        map.put("newRequestsCount", newRequestsCount );
        map.put("repliedRequestsCount", repliedRequestsCount);
        map.put("forInspectionRequestsCount",forInspectionRequestsCount);
        map.put("rejectedRequestsCount",  rejectedRequestsCount);

        return map;
    }



    private RequestView toRequestView(RequestEntity request) {
       return modelMapper.map(request, RequestView.class);
    }
}





