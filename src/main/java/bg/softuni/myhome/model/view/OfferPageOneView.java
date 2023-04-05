package bg.softuni.myhome.model.view;

import bg.softuni.myhome.model.enums.ConstructionEnum;
import bg.softuni.myhome.model.enums.HeatingEnum;
import bg.softuni.myhome.model.enums.OfferTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class OfferPageOneView {

    private String name;
    private OfferTypeEnum type;
    private String categoryName;
    private ConstructionEnum construction;
    private HeatingEnum heating;
    private BigDecimal price;
    private BigDecimal area;
    private String description;

    public String getName() {
        return name;
    }

    public OfferPageOneView setName(String name) {
        this.name = name;
        return this;
    }

    public OfferTypeEnum getType() {
        return type;
    }

    public OfferPageOneView setType(OfferTypeEnum type) {
        this.type = type;
        return this;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public OfferPageOneView setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public ConstructionEnum getConstruction() {
        return construction;
    }

    public OfferPageOneView setConstruction(ConstructionEnum construction) {
        this.construction = construction;
        return this;
    }

    public HeatingEnum getHeating() {
        return heating;
    }

    public OfferPageOneView setHeating(HeatingEnum heating) {
        this.heating = heating;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OfferPageOneView setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public BigDecimal getArea() {
        return area;
    }

    public OfferPageOneView setArea(BigDecimal area) {
        this.area = area;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OfferPageOneView setDescription(String description) {
        this.description = description;
        return this;
    }
}
