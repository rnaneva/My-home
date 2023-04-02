package bg.softuni.myhome.service;

import bg.softuni.myhome.model.dto.OfferAddPageTwoDTO;
import bg.softuni.myhome.model.entities.LocationEntity;
import bg.softuni.myhome.model.entities.OfferEntity;
import bg.softuni.myhome.model.entities.OfferPageTwo;
import bg.softuni.myhome.repository.OfferPageTwoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class OfferPageTwoService {

    private OfferPageTwoRepository offerPageTwoRepository;
    private ModelMapper modelMapper;
    private OfferService offerService;
    private LocationService locationService;



    public OfferPageTwoService(OfferPageTwoRepository offerPageTwoRepository, ModelMapper modelMapper, OfferService offerService, LocationService locationService) {
        this.offerPageTwoRepository = offerPageTwoRepository;
        this.modelMapper = modelMapper;
        this.offerService = offerService;
        this.locationService = locationService;
    }

    public OfferPageTwo savePageTwo(OfferAddPageTwoDTO offerAddPageTwoDTO){


        OfferPageTwo pageTwo = modelMapper.map(offerAddPageTwoDTO, OfferPageTwo.class);
        LocationEntity location =
                locationService.saveLocation(offerAddPageTwoDTO.getCityName(), offerAddPageTwoDTO.getAddress());

        pageTwo.setLocation(location);

        return offerPageTwoRepository.save(pageTwo);


    }
}
