package bg.softuni.myhome.model.dto;

import bg.softuni.myhome.model.enums.ConstructionEnum;
import bg.softuni.myhome.model.enums.HeatingEnum;
import bg.softuni.myhome.model.enums.OfferTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Objects;

public class OfferPageOneDTO {

    @NotBlank
    @Size(min = 5, max = 50, message = "Required-between 5 and 50 symbols")
    private String name;

    @NotNull(message = "Type of offer is required")
    private OfferTypeEnum type;

    @NotBlank(message = "Category of property is required")
    private String categoryName;

    @NotNull(message = "Construction of property is required")
    private ConstructionEnum construction;

    @NotNull(message = "Heating of property is required")
    private HeatingEnum heating;

    @NotNull(message = "Price of property is required")
    @Positive(message = "Price should be a positive number")
    private BigDecimal price;

    @NotNull(message = "Area of property is required")
    @Positive(message = "Area should be a positive number")
    private BigDecimal area;

    @NotBlank(message = "Description of property is required")
    @Size(min = 210, max = 2500 , message = "Description should be at least 210 characters and max 2500")
    private String description;

    public String getName() {
        return name;
    }

    public OfferPageOneDTO setName(String name) {
        this.name = name;
        return this;
    }

    public OfferTypeEnum getType() {
        return type;
    }

    public OfferPageOneDTO setType(OfferTypeEnum type) {
        this.type = type;
        return this;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public OfferPageOneDTO setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public ConstructionEnum getConstruction() {
        return construction;
    }

    public OfferPageOneDTO setConstruction(ConstructionEnum construction) {
        this.construction = construction;
        return this;
    }

    public HeatingEnum getHeating() {
        return heating;
    }

    public OfferPageOneDTO setHeating(HeatingEnum heating) {
        this.heating = heating;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OfferPageOneDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public BigDecimal getArea() {
        return area;
    }

    public OfferPageOneDTO setArea(BigDecimal area) {
        this.area = area;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OfferPageOneDTO setDescription(String description) {
        this.description = description;
        return this;
    }



}
