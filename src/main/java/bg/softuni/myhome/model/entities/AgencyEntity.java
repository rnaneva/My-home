package bg.softuni.myhome.model.entities;

import bg.softuni.myhome.model.enums.StatusEnum;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Entity
@Table(name = "agencies")
public class AgencyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String logoUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status;


    @OneToOne(optional = false)
    private UserEntity user;



    @OneToMany(mappedBy = "agency")
    private List<OfferEntity> offers;


    public AgencyEntity() {
        this.offers = new ArrayList<>();
    }


    public Long getId() {
        return id;
    }


    public AgencyEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AgencyEntity setName(String name) {
        this.name = name;
        return this;
    }


    public UserEntity getUser() {
        return user;
    }

    public AgencyEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }






    public String getAddress() {
        return address;
    }

    public AgencyEntity setAddress(String address) {
        this.address = address;
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
