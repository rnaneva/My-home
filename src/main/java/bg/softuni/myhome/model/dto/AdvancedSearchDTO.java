package bg.softuni.myhome.model.dto;

import bg.softuni.myhome.model.enums.ConstructionEnum;
import bg.softuni.myhome.model.enums.HeatingEnum;
import bg.softuni.myhome.model.enums.OfferTypeEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class AdvancedSearchDTO {
    @NotNull(message = "Type is required")
    private OfferTypeEnum type;
    @NotNull(message = "Category is required")
    private String category;
    @NotNull(message = "City is required")
    private String city;

    @NotNull(message = "Construction is required")
    private ConstructionEnum construction;

    @NotNull(message = "Heating is required")
    private HeatingEnum heating;

    @Positive
    @NotNull(message = "Price is required")
    private BigDecimal maxPrice;
    @Positive
    @NotNull(message = "Area is required")
    private BigDecimal minArea;

    @NotNull(message = "Agency is required")
    private String agencyName;

    private long userId;


    public OfferTypeEnum getType() {
        return type;
    }

    public AdvancedSearchDTO setType(OfferTypeEnum type) {
        this.type = type;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public AdvancedSearchDTO setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getCity() {
        return city;
    }

    public AdvancedSearchDTO setCity(String city) {
        this.city = city;
        return this;
    }

    public ConstructionEnum getConstruction() {
        return construction;
    }

    public AdvancedSearchDTO setConstruction(ConstructionEnum construction) {
        this.construction = construction;
        return this;
    }

    public HeatingEnum getHeating() {
        return heating;
    }

    public AdvancedSearchDTO setHeating(HeatingEnum heating) {
        this.heating = heating;
        return this;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public AdvancedSearchDTO setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
        return this;
    }

    public BigDecimal getMinArea() {
        return minArea;
    }

    public AdvancedSearchDTO setMinArea(BigDecimal minArea) {
        this.minArea = minArea;
        return this;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public AdvancedSearchDTO setAgencyName(String agencyName) {
        this.agencyName = agencyName;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public AdvancedSearchDTO setUserId(long userId) {
        this.userId = userId;
        return this;
    }
}
