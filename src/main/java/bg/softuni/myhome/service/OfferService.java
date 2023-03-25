package bg.softuni.myhome.service;

import bg.softuni.myhome.model.dto.AdvancedSearchDTO;
import bg.softuni.myhome.model.dto.OfferDTO;

import bg.softuni.myhome.model.dto.QuickSearchDTO;
import bg.softuni.myhome.model.entities.OfferEntity;
import bg.softuni.myhome.model.enums.OfferTypeEnum;
import bg.softuni.myhome.repository.OfferRepository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OfferService {


   private final OfferRepository offerRepository;

    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Transactional
    public List<OfferDTO> allRentProperties(){
        List<OfferEntity> offerEntities = offerRepository
                .findByOfferPage1Type(OfferTypeEnum.RENT);
//todo empty list
        return offerEntities
                .stream()
                .map(this::toOfferDTO)
                .toList();
    }

    @Transactional
    public List<OfferDTO> allSaleProperties(){
        List<OfferEntity> offerEntities = offerRepository
                .findByOfferPage1Type(OfferTypeEnum.SALE);
// todo empty list
        return offerEntities
                .stream()
                .map(this::toOfferDTO)
                .toList();
    }

    @Transactional
    public List<OfferDTO> findOffersQuickSearch(QuickSearchDTO dto){
        return offerRepository
                .findOffersQuickSearch(dto.getType(), dto.getCategory(), dto.getCity())
                .stream().map(this::toOfferDTO)
                .toList();
    }

//    @Transactional
//    public   List<OfferDTO> findOffersAdvancedSearch(AdvancedSearchDTO dto){
//        return offerRepository.findOffersAdvancedSearch(dto.getType(), dto.getCategory(), dto.getCity(),
//                dto.getConstruction(), dto.getHeating(), dto.getMaxPrice(), dto.getMinArea(), dto.getAgencyName())
//                .stream().map(this::toOfferDTO)
//                .toList();
//
//    }

    private OfferDTO toOfferDTO(OfferEntity offer) {

        String desc = offer.getOfferPage1().getDescription().substring(0, 215) + "...";

        return new OfferDTO()
                .setName(offer.getOfferPage1().getName())
                .setImageURL(offer.coverPhoto())
                .setArea(offer.getOfferPage1().getArea())
                .setFloor(offer.getOfferPage2().getFloor())
                .setAllFloors(offer.getOfferPage2().getAllFloors())
                .setBedrooms(offer.getOfferPage2().getBedrooms())
                .setBathrooms(offer.getOfferPage2().getBathrooms())
                .setBalconies(offer.getOfferPage2().getBalconies())
                .setDescription(desc)
                .setAddress(offer.fullAddress())
                .setPrice(offer.getOfferPage1().getPrice());
    }


}
