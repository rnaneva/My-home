package bg.softuni.myhome.model.entities;

import bg.softuni.myhome.model.enums.ConstructionEnum;
import bg.softuni.myhome.model.enums.HeatingEnum;
import bg.softuni.myhome.model.enums.OfferTypeEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "offers_first_page")
public class OfferPage1 extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OfferTypeEnum type;

    @ManyToOne
    private CategoryEntity category;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ConstructionEnum construction;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private HeatingEnum heating;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private BigDecimal area;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    public String getName() {
        return name;
    }

    public OfferPage1 setName(String name) {
        this.name = name;
        return this;
    }

    public OfferTypeEnum getType() {
        return type;
    }

    public OfferPage1 setType(OfferTypeEnum type) {
        this.type = type;
        return this;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public OfferPage1 setCategory(CategoryEntity category) {
        this.category = category;
        return this;
    }

    public ConstructionEnum getConstruction() {
        return construction;
    }

    public OfferPage1 setConstruction(ConstructionEnum construction) {
        this.construction = construction;
        return this;
    }

    public HeatingEnum getHeating() {
        return heating;
    }

    public OfferPage1 setHeating(HeatingEnum heating) {
        this.heating = heating;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OfferPage1 setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public BigDecimal getArea() {
        return area;
    }

    public OfferPage1 setArea(BigDecimal area) {
        this.area = area;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OfferPage1 setDescription(String description) {
        this.description = description;
        return this;
    }
}