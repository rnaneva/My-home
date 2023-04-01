package bg.softuni.myhome.service;

import bg.softuni.myhome.model.dto.SearchFormDTO;
import bg.softuni.myhome.model.enums.*;
import bg.softuni.myhome.model.view.AgencyView;
import bg.softuni.myhome.model.view.OfferAgencyView;
import bg.softuni.myhome.model.view.OfferDetailsView;
import bg.softuni.myhome.model.view.OfferView;

import bg.softuni.myhome.model.entities.OfferEntity;
import bg.softuni.myhome.repository.OfferRepository;


import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class OfferService {


    private final OfferRepository offerRepository;
    private final AgencyService agencyService;
    private ModelMapper modelMapper;

    @Autowired
    public OfferService(OfferRepository offerRepository, AgencyService agencyService, ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.agencyService = agencyService;
        this.modelMapper = modelMapper;
    }


    public List<OfferView> allRentProperties() {
        List<OfferEntity> offerEntities = offerRepository
                .findByOfferPage1Type(OfferTypeEnum.RENT);

        return offerEntities
                .stream()
                .map(this::toOfferView)
                .toList();
    }


    public List<OfferView> allSaleProperties() {
        List<OfferEntity> offerEntities = offerRepository
                .findByOfferPage1Type(OfferTypeEnum.SALE);

        return offerEntities
                .stream()
                .map(this::toOfferView)
                .toList();
    }


    public List<OfferView> findOffersBySearchForm(SearchFormDTO dto) {
        return offerRepository.findOffersBySearchForm(dto.getType(), dto.getCategoryName(), dto.getCityName(),
                        dto.getConstruction(), dto.getHeating(), dto.getMaxPrice(), dto.getMinArea(), dto.getAgencyName())
                .stream().map(this::toOfferView)
                .toList();

    }

    @Transactional
    public List<OfferView> findLastFourAddedOffers() {
        return offerRepository.findLast4AddedOffers()
                .stream().map(this::toOfferView)
                .toList();

    }

    @Transactional
    public List<OfferAgencyView> getOffersAgencyViewByStatus(String userVisibleId, StatusEnum status) {

        return  offerRepository.findByAgency_User_VisibleIdAndStatus(userVisibleId, status)
                .stream()
                .filter(o -> o.getStatus().equals(status))
                .map(this::toOfferAgencyView)
                .toList();

    }

    @Transactional
    public Map<String, Integer> getOffersCountForModel(String userVisibleId) {
        Map<String,Integer> map = new HashMap<>();

        Arrays.stream(StatusEnum.values())
                .forEach(statusEnum -> map.put(statusEnum.name().toLowerCase() + "OffersCount",
                        getOffersAgencyViewByStatus(userVisibleId, statusEnum).size()));

        return map;
    }


    private OfferAgencyView toOfferAgencyView(OfferEntity offer){
        return modelMapper.map(offer, OfferAgencyView.class);
    }



    public OfferDetailsView findDetailedOfferByVisibleId(String visibleId) {


        return offerRepository
                .findByVisibleId(visibleId)
                .map(this::toOfferDetailedView)
                .orElse(null);
    }

    public OfferEntity getOfferByVisibleId(String visibleId) {
        return offerRepository.findByVisibleId(visibleId)
                .orElse(null);
    }


    private OfferDetailsView toOfferDetailedView(OfferEntity offer) {
        return new OfferDetailsView()
                .setName(offer.getOfferPage1().getName())
                .setArea(offer.getOfferPage1().getArea())
                .setFloorInfo(offer.floorInfo())
                .setPlan(offer.plan())
                .setDescription(offer.getOfferPage1().getDescription())
                .setAddress(offer.fullAddress())
                .setCity(getCityName(offer))
                .setPrice(offer.getOfferPage1().getPrice())
                .setAgencyLogoUrl(offer.getAgency().getLogoUrl())
                .setCreatedOn(dateToString(offer))
                .setVisibleId(offer.getVisibleId())
                .setRating(offer.getRating())
                .setCategory(offer.getOfferPage1().getCategory())
                .setConstruction(constrToLowerCase(offer))
                .setConstructionYear(offer.getOfferPage2().getConstructionYear())
                .setHeating(heatingToLowerCase(offer))
                .setElevator(offer.getOfferPage2().getElevator())
                .setParking(offer.getOfferPage2().getParking())
                .setType(offer.getOfferPage1().getType())
                .setImages(offer.getPictures());

    }


    private OfferView toOfferView(OfferEntity offer) {

        return new OfferView()
                .setName(offer.getOfferPage1().getName())
                .setImageURL(offer.coverPhoto())
                .setArea(offer.getOfferPage1().getArea())
                .setFloorInfo(offer.floorInfo())
                .setPlan(offer.plan())
                .setDescription(descrSummary(offer))
                .setAddress(offer.fullAddress())
                .setPrice(offer.getOfferPage1().getPrice())
                .setCity(getCityName(offer))
                .setVisibleId(offer.getVisibleId());
    }

    private String descrSummary(OfferEntity offer) {
        return offer.getOfferPage1().getDescription().substring(0, 200) + "...";
    }

    private String getCityName(OfferEntity offer) {
        return offer.getOfferPage2().getLocation().getCity().getName();
    }

    private String constrToLowerCase(OfferEntity offer) {
        return offer.getOfferPage1().getConstruction().name().toLowerCase();
    }

    private String heatingToLowerCase(OfferEntity offer) {
        return offer.getOfferPage1().getHeating().name().toLowerCase();
    }

//    private List<String> getPictureUrls(OfferEntity offer) {
//        return offer.getPictures().stream().map(PictureEntity::getUrl).toList();
//    }

    private String dateToString(OfferEntity offer) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(offer.getCreatedOn());
    }


}
