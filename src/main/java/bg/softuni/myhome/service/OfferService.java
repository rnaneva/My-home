package bg.softuni.myhome.service;

import bg.softuni.myhome.model.dto.SearchDTO;
import bg.softuni.myhome.model.dto.OfferDTO;

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

        return offerEntities
                .stream()
                .map(this::toOfferDTO)
                .toList();
    }

    @Transactional
    public List<OfferDTO> allSaleProperties(){
        List<OfferEntity> offerEntities = offerRepository
                .findByOfferPage1Type(OfferTypeEnum.SALE);

        return offerEntities
                .stream()
                .map(this::toOfferDTO)
                .toList();
    }



    @Transactional
    public List<OfferDTO> findOffersBySearchForm(SearchDTO dto){
        return offerRepository.findOffersBySearchForm(dto.getType(), dto.getCategoryName(), dto.getCityName(),
                dto.getConstruction(), dto.getHeating(), dto.getMaxPrice(), dto.getMinArea(), dto.getAgencyName())
                .stream().map(this::toOfferDTO)
                .toList();

    }

    @Transactional
     public List<OfferDTO> findLast4AddedOffers(){
        return  offerRepository.findLast4AddedOffers()
               .stream().map(this::toOfferDTO)
                .toList();

    }



    private OfferDTO toOfferDTO(OfferEntity offer) {

        String desc = offer.getOfferPage1().getDescription().substring(0, 200) + "...";

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
