package bg.softuni.myhome.model.view;

import bg.softuni.myhome.model.entities.CategoryEntity;
import bg.softuni.myhome.model.entities.PictureEntity;
import bg.softuni.myhome.model.enums.*;

import java.math.BigDecimal;
import java.util.List;

public class OfferDetailsView {

    private String visibleId;
    private String createdOn;
    private String agencyLogoUrl;
    private RatingEnum rating;
    private String name;
    private String address;
    private String city;
    private BigDecimal area;
    private String floorInfo;
    private String plan;
    private String description;
    private BigDecimal price;
    private CategoryEntity category;
    private String construction;
    private String heating;
    private Integer constructionYear;
    private AvailableEnum parking;
    private AvailableEnum elevator;
    private List<PictureEntity> images;
    private OfferTypeEnum type;

    public List<PictureEntity> getImages() {
        return images;
    }

    public OfferDetailsView setImages(List<PictureEntity> images) {
        this.images = images;
        return this;
    }

    public String getCity() {
        return city;
    }

    public OfferDetailsView setCity(String city) {
        this.city = city;
        return this;
    }

    public String getPlan() {
        return plan;
    }

    public OfferDetailsView setPlan(String plan) {
        this.plan = plan;
        return this;
    }

    public OfferTypeEnum getType() {
        return type;
    }

    public OfferDetailsView setType(OfferTypeEnum type) {
        this.type = type;
        return this;
    }

    public String getVisibleId() {
        return visibleId;
    }

    public OfferDetailsView setVisibleId(String visibleId) {
        this.visibleId = visibleId;
        return this;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public OfferDetailsView setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public String getAgencyLogoUrl() {
        return agencyLogoUrl;
    }

    public OfferDetailsView setAgencyLogoUrl(String agencyLogoUrl) {
        this.agencyLogoUrl = agencyLogoUrl;
        return this;
    }

    public RatingEnum getRating() {
        return rating;
    }

    public OfferDetailsView setRating(RatingEnum rating) {
        this.rating = rating;
        return this;
    }

    public String getName() {
        return name;
    }

    public OfferDetailsView setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public OfferDetailsView setAddress(String address) {
        this.address = address;
        return this;
    }

    public BigDecimal getArea() {
        return area;
    }

    public OfferDetailsView setArea(BigDecimal area) {
        this.area = area;
        return this;
    }

    public String getFloorInfo() {
        return floorInfo;
    }

    public OfferDetailsView setFloorInfo(String floorInfo) {
        this.floorInfo = floorInfo;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OfferDetailsView setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OfferDetailsView setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public OfferDetailsView setCategory(CategoryEntity category) {
        this.category = category;
        return this;
    }

    public String getConstruction() {
        return construction;
    }

    public OfferDetailsView setConstruction(String construction) {
        this.construction = construction;
        return this;
    }

    public String getHeating() {
        return heating;
    }

    public OfferDetailsView setHeating(String heating) {
        this.heating = heating;
        return this;
    }

    public Integer getConstructionYear() {
        return constructionYear;
    }

    public OfferDetailsView setConstructionYear(Integer constructionYear) {
        this.constructionYear = constructionYear;
        return this;
    }

    public AvailableEnum getParking() {
        return parking;
    }

    public OfferDetailsView setParking(AvailableEnum parking) {
        this.parking = parking;
        return this;
    }

    public AvailableEnum getElevator() {
        return elevator;
    }

    public OfferDetailsView setElevator(AvailableEnum elevator) {
        this.elevator = elevator;
        return this;
    }


}
