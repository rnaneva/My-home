package bg.softuni.myhome.model.dto;

import bg.softuni.myhome.model.enums.ConstructionEnum;
import bg.softuni.myhome.model.enums.HeatingEnum;
import bg.softuni.myhome.model.enums.OfferTypeEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class SearchDTO {

//    todo if user is logged

    @NotNull(message = "Type of offer is required")
    private OfferTypeEnum type;

    @NotNull(message = "Category of the property is required")
    private String categoryName;

    @NotNull(message = "Location of the proprety  is required")
    private String cityName;

    private ConstructionEnum construction;


    private HeatingEnum heating;

    @Positive(message = "Price should be positive number")
    private BigDecimal maxPrice;

    @Positive(message = "Area should be positive number")
    private BigDecimal minArea;

    private String agencyName;

    private long userId;

    private String visible_id;

    public String getVisible_id() {
        return visible_id;
    }

    public SearchDTO setVisible_id(String visible_id) {
        this.visible_id = visible_id;
        return this;
    }

    public OfferTypeEnum getType() {
        return type;
    }

    public SearchDTO setType(OfferTypeEnum type) {
        this.type = type;
        return this;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public SearchDTO setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public String getCityName() {
        return cityName;
    }

    public SearchDTO setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public ConstructionEnum getConstruction() {
        return construction;
    }

    public SearchDTO setConstruction(ConstructionEnum construction) {
        this.construction = construction;
        return this;
    }

    public HeatingEnum getHeating() {
        return heating;
    }

    public SearchDTO setHeating(HeatingEnum heating) {
        this.heating = heating;
        return this;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public SearchDTO setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
        return this;
    }

    public BigDecimal getMinArea() {
        return minArea;
    }

    public SearchDTO setMinArea(BigDecimal minArea) {
        this.minArea = minArea;
        return this;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public SearchDTO setAgencyName(String agencyName) {
        this.agencyName = agencyName;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public SearchDTO setUserId(long userId) {
        this.userId = userId;
        return this;
    }
}
