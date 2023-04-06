package bg.softuni.myhome.model.entities;

import bg.softuni.myhome.model.enums.ConstructionEnum;
import bg.softuni.myhome.model.enums.HeatingEnum;
import bg.softuni.myhome.model.enums.OfferTypeEnum;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "search_criteria")
public class SearchEntity extends BaseEntity {


    @Enumerated(EnumType.STRING)
    private OfferTypeEnum type;

    @ManyToOne
    private CategoryEntity category;

    @Enumerated(EnumType.STRING)
    private ConstructionEnum construction;

    @Enumerated(EnumType.STRING)
    private HeatingEnum heating;

    private BigDecimal maxPrice;

    private BigDecimal minArea;

    @ManyToOne
    private CityEntity city;

    @ManyToOne
    private AgencyEntity agency;

    @OneToOne
    private UserEntity user;

    private String visibleId;

    private String sortBy;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate receivedOn;

    public LocalDate getReceivedOn() {
        return receivedOn;
    }

    public SearchEntity setReceivedOn(LocalDate receivedOn) {
        this.receivedOn = receivedOn;
        return this;
    }

    public String getSortBy() {
        return sortBy;
    }

    public SearchEntity setSortBy(String sortBy) {
        this.sortBy = sortBy;
        return this;
    }

    public String getVisibleId() {
        return visibleId;
    }

    public SearchEntity setVisibleId(String visibleId) {
        this.visibleId = visibleId;
        return this;
    }

    public UserEntity getUserEntity() {
        return user;
    }

    public SearchEntity setUserEntity(UserEntity userEntity) {
        this.user = userEntity;
        return this;
    }

    public OfferTypeEnum getType() {
        return type;
    }

    public SearchEntity setType(OfferTypeEnum type) {
        this.type = type;
        return this;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public SearchEntity setCategory(CategoryEntity category) {
        this.category = category;
        return this;
    }

    public ConstructionEnum getConstruction() {
        return construction;
    }

    public SearchEntity setConstruction(ConstructionEnum construction) {
        this.construction = construction;
        return this;
    }

    public HeatingEnum getHeating() {
        return heating;
    }

    public SearchEntity setHeating(HeatingEnum heating) {
        this.heating = heating;
        return this;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public SearchEntity setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
        return this;
    }

    public BigDecimal getMinArea() {
        return minArea;
    }

    public SearchEntity setMinArea(BigDecimal minArea) {
        this.minArea = minArea;
        return this;
    }

    public CityEntity getCity() {
        return city;
    }

    public SearchEntity setCity(CityEntity city) {
        this.city = city;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public SearchEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public AgencyEntity getAgency() {
        return agency;
    }

    public SearchEntity setAgency(AgencyEntity agency) {
        this.agency = agency;
        return this;
    }
}
