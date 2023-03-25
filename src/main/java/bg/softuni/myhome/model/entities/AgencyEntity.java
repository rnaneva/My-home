package bg.softuni.myhome.model.entities;

import bg.softuni.myhome.model.enums.StatusEnum;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Entity
@Table(name = "agencies")
public class AgencyEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(optional = false)
    private LocationEntity location;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String logoUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @OneToMany(mappedBy = "agency")
    private List<OfferEntity> offers;

    public AgencyEntity(String agencyName, LocationEntity address, String phoneNumber,
                        String logoUrl, StatusEnum status) {
        this.name = agencyName;
        this.location = address;
        this.phoneNumber = phoneNumber;
        this.logoUrl = logoUrl;
        this.status = status;
    }


    public AgencyEntity() {
        this.offers = new ArrayList<>();
    }


    public String getAgencyName() {
        return name;
    }


    public AgencyEntity setAgencyName(String agencyName) {
        this.name = agencyName;
        return this;
    }

    public LocationEntity getAddress() {
        return location;
    }

    public AgencyEntity setAddress(LocationEntity address) {
        this.location = address;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public AgencyEntity setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public AgencyEntity setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
        return this;
    }

    public List<OfferEntity> getOffers() {
        return Collections.unmodifiableList(offers);
    }

    public AgencyEntity setOffers(List<OfferEntity> offers) {
        this.offers = offers;
        return this;
    }

    public void addOffer(OfferEntity offer){
        this.offers.add(offer);
    }

    public StatusEnum getStatus() {
        return status;
    }

    public AgencyEntity setStatus(StatusEnum status) {
        this.status = status;
        return this;
    }
}
