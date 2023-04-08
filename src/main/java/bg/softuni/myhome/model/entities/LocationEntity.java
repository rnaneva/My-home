package bg.softuni.myhome.model.entities;

import jakarta.persistence.Entity;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "locations")
public class LocationEntity extends BaseEntity {

    @ManyToOne(optional = false)
    private CityEntity city;

    private String address;

    @OneToOne(mappedBy = "location")
    private OfferPageTwo offerPageTwo;

    public CityEntity getCity() {
        return city;
    }

    public LocationEntity setCity(CityEntity city) {
        this.city = city;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public LocationEntity setAddress(String address) {
        this.address = address;
        return this;
    }

    public OfferPageTwo getOfferPageTwo() {
        return offerPageTwo;
    }

    public LocationEntity setOfferPageTwo(OfferPageTwo offerPageTwo) {
        this.offerPageTwo = offerPageTwo;
        return this;
    }
}