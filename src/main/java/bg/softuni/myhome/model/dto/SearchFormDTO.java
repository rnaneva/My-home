package bg.softuni.myhome.model.dto;

import bg.softuni.myhome.model.enums.ConstructionEnum;
import bg.softuni.myhome.model.enums.HeatingEnum;
import bg.softuni.myhome.model.enums.OfferTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class SearchFormDTO {


    @NotNull(message = "Please chose offer for rent or sale")
    private OfferTypeEnum type;

    @NotBlank(message = "Category of the property is required")
    private String categoryName;

    @NotBlank(message = "Location of the property  is required")
    private String cityName;

    private ConstructionEnum construction;


    private HeatingEnum heating;

    @Positive(message = "Price should be positive number")
    private BigDecimal maxPrice;

    @Positive(message = "Area should be positive number")
    private BigDecimal minArea;

    private String agencyName;

    private String visibleId;

    private String sortBy;

    public String getSortBy() {
        return sortBy;
    }

    public SearchFormDTO setSortBy(String sortBy) {
        this.sortBy = sortBy;
        return this;
    }

    public String getVisible_id() {
        return visibleId;
    }

    public SearchFormDTO setVisible_id(String visible_id) {
        this.visibleId = visible_id;
        return this;
    }

    public OfferTypeEnum getType() {
        return type;
    }

    public SearchFormDTO setType(OfferTypeEnum type) {
        this.type = type;
        return this;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public SearchFormDTO setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public String getCityName() {
        return cityName;
    }

    public SearchFormDTO setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public ConstructionEnum getConstruction() {
        return construction;
    }

    public SearchFormDTO setConstruction(ConstructionEnum construction) {
        this.construction = construction;
        return this;
    }

    public HeatingEnum getHeating() {
        return heating;
    }

    public SearchFormDTO setHeating(HeatingEnum heating) {
        this.heating = heating;
        return this;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public SearchFormDTO setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
        return this;
    }

    public BigDecimal getMinArea() {
        return minArea;
    }

    public SearchFormDTO setMinArea(BigDecimal minArea) {
        this.minArea = minArea;
        return this;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public SearchFormDTO setAgencyName(String agencyName) {
        this.agencyName = agencyName;
        return this;
    }


}
