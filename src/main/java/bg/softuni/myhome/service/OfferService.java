package bg.softuni.myhome.service;

import bg.softuni.myhome.exception.ObjectNotFoundException;
import bg.softuni.myhome.model.dto.SearchFormDTO;
import bg.softuni.myhome.model.entities.*;
import bg.softuni.myhome.model.enums.*;
import bg.softuni.myhome.model.view.OfferAgencyView;
import bg.softuni.myhome.model.view.OfferDetailsView;
import bg.softuni.myhome.model.view.OfferView;

import bg.softuni.myhome.model.view.PictureView;
import bg.softuni.myhome.repository.OfferRepository;


import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class OfferService {


    private final OfferRepository offerRepository;
    private final AgencyService agencyService;
    private final ModelMapper modelMapper;

    @Autowired
    public OfferService(OfferRepository offerRepository, AgencyService agencyService,
                        ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.agencyService = agencyService;
        this.modelMapper = modelMapper;
    }


    public List<OfferView> allRentProperties() {
        List<OfferEntity> offerEntities = offerRepository
                .findByOfferPageOneType(OfferTypeEnum.RENT, StatusEnum.ACTIVE);

        return offerEntities
                .stream()
                .map(this::toOfferView)
                .toList();
    }


    public List<OfferView> allSaleProperties() {
        List<OfferEntity> offerEntities = offerRepository
                .findByOfferPageOneType(OfferTypeEnum.SALE, StatusEnum.ACTIVE);

        return offerEntities
                .stream()
                .map(this::toOfferView)
                .toList();
    }



    public List<OfferView> findOffersBySearchForm(SearchFormDTO dto) {
        List<OfferView> offers = new ArrayList<>(offerRepository.findOffersBySearchForm(dto.getType(),
                        dto.getCategoryName(),
                        dto.getCityName(), dto.getConstruction(), dto.getHeating(),
                        dto.getMaxPrice(), dto.getMinArea(), dto.getAgencyName())
                .stream()
                .filter(o -> o.getStatus().equals(StatusEnum.ACTIVE))
                .map(this::toOfferView)
                .toList());


        if (dto.getSortBy() != null) {
            switch (dto.getSortBy()) {
                case "price" -> offers.sort(Comparator.comparing(OfferView::getPrice));
                case "receivedOn" -> offers.sort(Comparator.comparing(OfferView::getCreatedOn).reversed());
            }
        }

        return offers;

    }

    @Transactional
    public List<OfferView> findLastFourAddedOffers() {
        return offerRepository.findLast4AddedOffers()
                .stream()
                .map(this::toOfferView)
                .toList();

    }

    @Transactional
    public List<OfferAgencyView> getOffersAgencyViewByStatus(String userVisibleId, StatusEnum status) {

        return offerRepository.findByAgency_User_VisibleIdAndStatus(userVisibleId, status)
                .stream()
                .map(this::toOfferAgencyView)
                .toList();

    }


    @Transactional
    public List<OfferView> getOffersByAgencyId(long id) {
        List<OfferEntity>offerEntities = agencyService.findOffersByAgencyId(id);

        return offerEntities
                .stream()
                .filter(offer -> offer.getStatus().equals(StatusEnum.ACTIVE))
                .map(this::toOfferView)
                .toList();
    }


    //    @Transactional
    public Map<String, Integer> getOffersCountForModel(String userVisibleId) {
        Map<String, Integer> map = new HashMap<>();

        Arrays.stream(StatusEnum.values())
                .forEach(statusEnum -> map.put(statusEnum.name().toLowerCase() + "OffersCount",
                        getOffersAgencyViewByStatus(userVisibleId, statusEnum).size()));

        return map;
    }


    private OfferAgencyView toOfferAgencyView(OfferEntity offer) {

        return modelMapper.map(offer, OfferAgencyView.class);

    }


    public OfferDetailsView findDetailedOfferByVisibleId(String visibleId) {

        return offerRepository
                .findOfferByVisibleId(visibleId)
                .map(this::toOfferDetailedView)
                .orElseThrow(() -> new ObjectNotFoundException("findDetailedOfferByVisibleId", visibleId));
    }


    public OfferEntity getOfferById(String visibleId) {
        OfferEntity offerWithPics = offerRepository.findOfferByVisibleId(visibleId)
                .orElse(null);
        OfferEntity offerWithoutPics = offerRepository.getOfferByVisibleIdWithoutPics(visibleId)
                .orElse(null);
        return offerWithPics != null ? offerWithPics : offerWithoutPics;
    }


    public OfferEntity createOfferWithPageOne(OfferPageOne offerPageOne, String userVisibleId) {

        OfferEntity offer = new OfferEntity()
                .setOfferPageOne(offerPageOne)
                .setStatus(StatusEnum.INACTIVE)
                .setAgency(agencyService.findAgencyByUserVisibleId(userVisibleId))
                .setVisibleId("111" + offerPageOne.getId());

        return offerRepository.save(offer);
    }


    public void addPageTwoToOffer(OfferPageTwo offerPageTwo, String offerVisibleId) {
        OfferEntity offer = getOfferById(offerVisibleId);
        offer.setOfferPageTwo(offerPageTwo);
        offerRepository.save(offer);
    }

    public void changeOfferStatus(OfferEntity offer, StatusEnum newStatus) {
        offer.setStatus(newStatus);
        offerRepository.save(offer);
    }

    @Transactional
    public List<PictureView> getOfferPicturesByVisibleId(String visibleId) {
        OfferEntity offer = getOfferById(visibleId);

        return offer.getPictures()
                .stream()
                .map(this::toPictureView)
                .toList();
    }

    private PictureView toPictureView(PictureEntity picture) {
        return new PictureView()
                .setId(picture.getId())
                .setUrl(picture.getUrl())
                .setName(picture.getTitle());
    }


    private OfferDetailsView toOfferDetailedView(OfferEntity offer) {
        return new OfferDetailsView()
                .setName(offer.getOfferPageOne().getName())
                .setArea(offer.getOfferPageOne().getArea())
                .setFloorInfo(offer.floorInfo())
                .setPlan(offer.plan())
                .setDescription(offer.getOfferPageOne().getDescription())
                .setAddress(offer.fullAddress())
                .setCity(getCityName(offer))
                .setPrice(offer.getOfferPageOne().getPrice())
                .setAgencyLogoUrl(offer.getAgency().getLogoUrl())
                .setCreatedOn(dateToString(offer))
                .setVisibleId(offer.getVisibleId())
                .setCategory(offer.getOfferPageOne().getCategory())
                .setConstruction(construction(offer))
                .setConstructionYear(offer.getOfferPageTwo().getConstructionYear())
                .setHeating(heating(offer))
                .setElevator(offer.getOfferPageTwo().getElevator())
                .setParking(offer.getOfferPageTwo().getParking())
                .setType(offer.getOfferPageOne().getType())
                .setImages(getPicturesView(offer));

    }

    private List<PictureView> getPicturesView(OfferEntity offer) {
        return offer.getPictures().stream().map(p -> modelMapper.map(p, PictureView.class)).collect(Collectors.toList());
    }


    private OfferView toOfferView(OfferEntity offer) {

        return new OfferView()
                .setName(offer.getOfferPageOne().getName())
                .setImageURL(offer.coverPhoto())
                .setArea(offer.getOfferPageOne().getArea())
                .setFloorInfo(offer.floorInfo())
                .setPlan(offer.plan())
                .setDescription(descrSummary(offer))
                .setAddress(offer.fullAddress())
                .setPrice(offer.getOfferPageOne().getPrice())
                .setCity(getCityName(offer))
                .setVisibleId(offer.getVisibleId())
                .setCreatedOn(offer.getCreatedOn())
                .setId(offer.getId());
    }

    private String descrSummary(OfferEntity offer) {
        return offer.getOfferPageOne().getDescription().substring(0, 200) + "...";
    }

    private String getCityName(OfferEntity offer) {
        return offer.getOfferPageTwo().getLocation().getCity().getName();
    }

    private String construction(OfferEntity offer) {
        return offer.getOfferPageOne().getConstruction().name();
    }

    private String heating(OfferEntity offer) {
        return offer.getOfferPageOne().getHeating().name();
    }


    private String dateToString(OfferEntity offer) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(offer.getCreatedOn());
    }


}
