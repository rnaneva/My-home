package bg.softuni.myhome.model.dto;

import bg.softuni.myhome.model.enums.AvailableEnum;
import com.fasterxml.jackson.datatype.jsr310.ser.YearSerializer;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.Year;
import java.util.Date;

public class OfferAddPageTwoDTO {


    @NotBlank(message = "City of property is required")
    private String cityName;

    @NotBlank(message = "Address of property is required")
    private String address;

    @NotNull(message = "Construction year of property is required")
    @Min(1900)
    @Max(2023)
    private Integer constructionYear;

    @NotNull(message = "Information for parking is required")
    private AvailableEnum parking;

    @NotNull(message = "Information for elevator is required")
    private AvailableEnum elevator;

    @NotNull(message = "Floor of property is required")
    @Positive(message = "Floor should be a positive number")
    private Integer floor;

    @NotNull(message = "Number of total floors of building is required")
    @Positive(message = "Floors should be a positive number")
    private Integer allFloors;

    @NotNull(message = "Number of bedrooms of property is required")
    @Positive(message = "Bedrooms should be a positive number")
    private Integer bedrooms;

    @NotNull(message = "Number of bathrooms of property is required")
    @Positive(message = "Bathrooms should be a positive number")
    private Integer bathrooms;

    @NotNull(message = "Number of balconies of property is required")
    @Positive(message = "Balconies should be a positive number")
    private Integer balconies;

    public String getCityName() {
        return cityName;
    }

    public OfferAddPageTwoDTO setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public OfferAddPageTwoDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public Integer getConstructionYear() {
        return constructionYear;
    }

    public OfferAddPageTwoDTO setConstructionYear(Integer constructionYear) {
        this.constructionYear = constructionYear;
        return this;
    }

    public AvailableEnum getParking() {
        return parking;
    }

    public OfferAddPageTwoDTO setParking(AvailableEnum parking) {
        this.parking = parking;
        return this;
    }

    public AvailableEnum getElevator() {
        return elevator;
    }

    public OfferAddPageTwoDTO setElevator(AvailableEnum elevator) {
        this.elevator = elevator;
        return this;
    }

    public Integer getFloor() {
        return floor;
    }

    public OfferAddPageTwoDTO setFloor(Integer floor) {
        this.floor = floor;
        return this;
    }

    public Integer getAllFloors() {
        return allFloors;
    }

    public OfferAddPageTwoDTO setAllFloors(Integer allFloors) {
        this.allFloors = allFloors;
        return this;
    }

    public Integer getBedrooms() {
        return bedrooms;
    }

    public OfferAddPageTwoDTO setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
        return this;
    }

    public Integer getBathrooms() {
        return bathrooms;
    }

    public OfferAddPageTwoDTO setBathrooms(Integer bathrooms) {
        this.bathrooms = bathrooms;
        return this;
    }

    public Integer getBalconies() {
        return balconies;
    }

    public OfferAddPageTwoDTO setBalconies(Integer balconies) {
        this.balconies = balconies;
        return this;
    }
}
