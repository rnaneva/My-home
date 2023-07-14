package bg.softuni.myhome.model.entities;

import bg.softuni.myhome.model.enums.AvailableEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "offers_page_two")
public class OfferPageTwo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne(optional = false)
    private LocationEntity location;

    @Column(nullable = false)
    private Integer constructionYear;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AvailableEnum parking;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AvailableEnum elevator;

    @Column(nullable = false)
    private Integer floor;

    @Column(nullable = false)
    private Integer allFloors;

    @Column(nullable = false)
    private Integer bedrooms;

    @Column(nullable = false)
    private Integer bathrooms;

    @Column(nullable = false)
    private Integer balconies;

    public OfferPageTwo(){}

    public Long getId() {
        return id;
    }

    public OfferPageTwo setId(Long id) {
        this.id = id;
        return this;
    }

    public LocationEntity getLocation() {
        return location;
    }

    public OfferPageTwo setLocation(LocationEntity location) {
        this.location = location;
        return this;
    }

    public Integer getConstructionYear() {
        return constructionYear;
    }

    public OfferPageTwo setConstructionYear(Integer constructionYear) {
        this.constructionYear = constructionYear;
        return this;
    }

    public AvailableEnum getParking() {
        return parking;
    }

    public OfferPageTwo setParking(AvailableEnum parking) {
        this.parking = parking;
        return this;
    }

    public AvailableEnum getElevator() {
        return elevator;
    }

    public OfferPageTwo setElevator(AvailableEnum elevator) {
        this.elevator = elevator;
        return this;
    }

    public Integer getFloor() {
        return floor;
    }

    public OfferPageTwo setFloor(Integer floor) {
        this.floor = floor;
        return this;
    }

    public Integer getAllFloors() {
        return allFloors;
    }

    public OfferPageTwo setAllFloors(Integer allFloors) {
        this.allFloors = allFloors;
        return this;
    }

    public Integer getBedrooms() {
        return bedrooms;
    }

    public OfferPageTwo setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
        return this;
    }

    public Integer getBathrooms() {
        return bathrooms;
    }

    public OfferPageTwo setBathrooms(Integer bathrooms) {
        this.bathrooms = bathrooms;
        return this;
    }

    public Integer getBalconies() {
        return balconies;
    }

    public OfferPageTwo setBalconies(Integer balconies) {
        this.balconies = balconies;
        return this;
    }
}