package bg.softuni.myhome.model.view;

import bg.softuni.myhome.model.enums.ConstructionEnum;
import bg.softuni.myhome.model.enums.HeatingEnum;
import bg.softuni.myhome.model.enums.OfferTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SearchView {
    private long id;

    private OfferTypeEnum type;

    private String categoryName;

    private String cityName;

    private ConstructionEnum construction;

    private HeatingEnum heating;

    private BigDecimal maxPrice;

    private BigDecimal minArea;

    private String agencyName;

    private String visibleId;

    private String sortBy;

    private String email;

    private String userNames;

    private LocalDate receivedOn;

    public long getId() {
        return id;
    }

    public SearchView setId(long id) {
        this.id = id;
        return this;
    }

    public OfferTypeEnum getType() {
        return type;
    }

    public SearchView setType(OfferTypeEnum type) {
        this.type = type;
        return this;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public SearchView setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public String getCityName() {
        return cityName;
    }

    public SearchView setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public ConstructionEnum getConstruction() {
        return construction;
    }

    public SearchView setConstruction(ConstructionEnum construction) {
        this.construction = construction;
        return this;
    }

    public HeatingEnum getHeating() {
        return heating;
    }

    public SearchView setHeating(HeatingEnum heating) {
        this.heating = heating;
        return this;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public SearchView setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
        return this;
    }

    public BigDecimal getMinArea() {
        return minArea;
    }

    public SearchView setMinArea(BigDecimal minArea) {
        this.minArea = minArea;
        return this;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public SearchView setAgencyName(String agencyName) {
        this.agencyName = agencyName;
        return this;
    }

    public String getVisibleId() {
        return visibleId;
    }

    public SearchView setVisibleId(String visibleId) {
        this.visibleId = visibleId;
        return this;
    }

    public String getSortBy() {
        return sortBy;
    }

    public SearchView setSortBy(String sortBy) {
        this.sortBy = sortBy;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public SearchView setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getUserNames() {
        return userNames;
    }

    public SearchView setUserNames(String userNames) {
        this.userNames = userNames;
        return this;
    }

    public LocalDate getReceivedOn() {
        return receivedOn;
    }

    public SearchView setReceivedOn(LocalDate receivedOn) {
        this.receivedOn = receivedOn;
        return this;
    }
}
