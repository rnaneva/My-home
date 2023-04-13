package bg.softuni.myhome.service;

import bg.softuni.myhome.model.dto.OfferPageTwoDTO;
import bg.softuni.myhome.model.entities.CityEntity;
import bg.softuni.myhome.model.entities.LocationEntity;
import bg.softuni.myhome.model.entities.OfferEntity;
import bg.softuni.myhome.model.entities.OfferPageTwo;
import bg.softuni.myhome.model.view.OfferPageTwoView;
import bg.softuni.myhome.repository.OfferPageTwoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class OfferPageTwoService {

    private final OfferPageTwoRepository offerPageTwoRepository;
    private final ModelMapper modelMapper;
    private final OfferService offerService;
    private final LocationService locationService;


    public OfferPageTwoService(OfferPageTwoRepository offerPageTwoRepository,
                               ModelMapper modelMapper, OfferService offerService,
                               LocationService locationService) {
        this.offerPageTwoRepository = offerPageTwoRepository;
        this.modelMapper = modelMapper;
        this.offerService = offerService;
        this.locationService = locationService;
    }


    public OfferPageTwo savePageTwo(OfferPageTwoDTO offerPageTwoDTO){


        OfferPageTwo pageTwo = modelMapper.map(offerPageTwoDTO, OfferPageTwo.class);
        LocationEntity location =
                locationService.saveLocation(offerPageTwoDTO.getCityName(),
                        offerPageTwoDTO.getAddress());

        pageTwo.setLocation(location);

        return offerPageTwoRepository.save(pageTwo);


    }

    public void editPageTwo(OfferPageTwo offerPageTwo, OfferPageTwoDTO dto) {
        LocationEntity location =
                locationService.saveLocation(dto.getCityName(), dto.getAddress());

        offerPageTwo.
                setLocation(location)
                .setAllFloors(dto.getAllFloors())
                .setBalconies(dto.getBalconies())
                .setBathrooms(dto.getBathrooms())
                .setBedrooms(dto.getBedrooms())
                .setConstructionYear(dto.getConstructionYear())
                .setFloor(dto.getFloor())
                .setParking(dto.getParking())
                .setElevator(dto.getElevator());

        offerPageTwoRepository.save(offerPageTwo);
    }



    public OfferPageTwoView getOfferPageTwoViewByVisibleId(OfferPageTwo offer){
        return  modelMapper.map(offer, OfferPageTwoView.class);

    }

     // can return null
    public OfferPageTwo getOfferPageTwoByOfferVisibleId(String visibleId){
        return offerService.getOfferById(visibleId).getOfferPageTwo();
    }


}
