package bg.softuni.myhome.service;

import bg.softuni.myhome.model.dto.RequestDTO;
import bg.softuni.myhome.model.entities.OfferEntity;
import bg.softuni.myhome.model.entities.RequestEntity;
import bg.softuni.myhome.model.enums.RequestStatusEnum;
import bg.softuni.myhome.repository.RequestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RequestService {

    private final RequestRepository requestRepository;
    private final OfferService offerService;


    public RequestService(RequestRepository requestRepository, OfferService offerService) {
        this.requestRepository = requestRepository;
        this.offerService = offerService;
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
}





