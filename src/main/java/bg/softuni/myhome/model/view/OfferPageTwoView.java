package bg.softuni.myhome.model.view;

import bg.softuni.myhome.model.enums.AvailableEnum;
import jakarta.validation.constraints.*;

public class OfferPageTwoView {

    private String locationCityName;
    private String locationAddress;
    private Integer constructionYear;
    private AvailableEnum parking;
    private AvailableEnum elevator;
    private Integer floor;
    private Integer allFloors;
    private Integer bedrooms;
    private Integer bathrooms;
    private Integer balconies;

    public String getLocationCityName() {
        return locationCityName;
    }

    public OfferPageTwoView setLocationCityName(String locationCityName) {
        this.locationCityName = locationCityName;
        return this;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public OfferPageTwoView setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
        return this;
    }

    public Integer getConstructionYear() {
        return constructionYear;
    }

    public OfferPageTwoView setConstructionYear(Integer constructionYear) {
        this.constructionYear = constructionYear;
        return this;
    }

    public AvailableEnum getParking() {
        return parking;
    }

    public OfferPageTwoView setParking(AvailableEnum parking) {
        this.parking = parking;
        return this;
    }

    public AvailableEnum getElevator() {
        return elevator;
    }

    public OfferPageTwoView setElevator(AvailableEnum elevator) {
        this.elevator = elevator;
        return this;
    }

    public Integer getFloor() {
        return floor;
    }

    public OfferPageTwoView setFloor(Integer floor) {
        this.floor = floor;
        return this;
    }

    public Integer getAllFloors() {
        return allFloors;
    }

    public OfferPageTwoView setAllFloors(Integer allFloors) {
        this.allFloors = allFloors;
        return this;
    }

    public Integer getBedrooms() {
        return bedrooms;
    }

    public OfferPageTwoView setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
        return this;
    }

    public Integer getBathrooms() {
        return bathrooms;
    }

    public OfferPageTwoView setBathrooms(Integer bathrooms) {
        this.bathrooms = bathrooms;
        return this;
    }

    public Integer getBalconies() {
        return balconies;
    }

    public OfferPageTwoView setBalconies(Integer balconies) {
        this.balconies = balconies;
        return this;
    }
}
