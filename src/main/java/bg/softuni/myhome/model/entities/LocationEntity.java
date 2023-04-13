package bg.softuni.myhome.model.entities;

import jakarta.persistence.*;


@Entity
@Table(name = "locations")
public class LocationEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private CityEntity city;

    private String address;

    @OneToOne(mappedBy = "location")
    private OfferPageTwo offerPageTwo;

    public Long getId() {
        return id;
    }

    public LocationEntity setId(Long id) {
        this.id = id;
        return this;
    }

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