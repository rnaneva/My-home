package bg.softuni.myhome.model.entities;

import bg.softuni.myhome.model.dto.OfferPageOneDTO;
import bg.softuni.myhome.model.enums.ConstructionEnum;
import bg.softuni.myhome.model.enums.HeatingEnum;
import bg.softuni.myhome.model.enums.OfferTypeEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "offers_page_one")
public class OfferPageOne  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
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


    public Long getId() {
        return id;
    }

    public OfferPageOne setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public OfferPageOne setName(String name) {
        this.name = name;
        return this;
    }

    public OfferTypeEnum getType() {
        return type;
    }

    public OfferPageOne setType(OfferTypeEnum type) {
        this.type = type;
        return this;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public OfferPageOne setCategory(CategoryEntity category) {
        this.category = category;
        return this;
    }

    public ConstructionEnum getConstruction() {
        return construction;
    }

    public OfferPageOne setConstruction(ConstructionEnum construction) {
        this.construction = construction;
        return this;
    }

    public HeatingEnum getHeating() {
        return heating;
    }

    public OfferPageOne setHeating(HeatingEnum heating) {
        this.heating = heating;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OfferPageOne setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public BigDecimal getArea() {
        return area;
    }

    public OfferPageOne setArea(BigDecimal area) {
        this.area = area;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OfferPageOne setDescription(String description) {
        this.description = description;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof OfferPageOne that)) return false;
        return Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && type == that.type
                && Objects.equals(category, that.category)
                && construction == that.construction
                && heating == that.heating
                && Objects.equals(price, that.price)
                && Objects.equals(area, that.area)
                && Objects.equals(description, that.description);
    }


}