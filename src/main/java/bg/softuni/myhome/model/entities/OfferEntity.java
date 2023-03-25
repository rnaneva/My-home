package bg.softuni.myhome.model.entities;

import bg.softuni.myhome.model.enums.*;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "offers")
public class OfferEntity extends BaseEntity {

    @OneToOne(fetch = FetchType.EAGER)
    private OfferPage1 offerPage1;

    @OneToOne(fetch = FetchType.EAGER)
    private OfferPage2 offerPage2;

//    Page 3

    @OneToMany
    private List<PictureEntity> pictures;


//

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Column(nullable = false)
    private Integer visibleId;

    @DateTimeFormat(pattern = "dd-MM-yy")
    @Column(nullable = false)
    private LocalDate createdOn;

    @ManyToOne
    private AgencyEntity agency;

    private boolean isDeleted;

//    todo comments
    @Enumerated(EnumType.STRING)
    private RatingEnum rating;

    public String coverPhoto(){
        return this.getPictures().get(0).getUrl();
    }

    public String fullAddress(){
        return this.offerPage2.getLocation().getCity().getName() + ", "
                + this.getOfferPage2().getLocation().getAddress();
    }


    public OfferEntity() {
        this.pictures = new ArrayList<>();
    }

    public OfferPage1 getOfferPage1() {
        return offerPage1;
    }

    public OfferEntity setOfferPage1(OfferPage1 offerPage1) {
        this.offerPage1 = offerPage1;
        return this;
    }

    public OfferPage2 getOfferPage2() {
        return offerPage2;
    }

    public OfferEntity setOfferPage2(OfferPage2 offerPage2) {
        this.offerPage2 = offerPage2;
        return this;
    }

    public List<PictureEntity> getPictures() {
        return Collections.unmodifiableList(pictures);
    }

    public OfferEntity setPictures(List<PictureEntity> pictures) {
        this.pictures = pictures;
        return this;
    }

    public void addPicture(PictureEntity picture){
        this.pictures.add(picture);
    }

    public StatusEnum getStatus() {
        return status;
    }

    public OfferEntity setStatus(StatusEnum status) {
        this.status = status;
        return this;
    }

    public Integer getVisibleId() {
        return visibleId;
    }

    public OfferEntity setVisibleId(Integer visibleId) {
        this.visibleId = visibleId;
        return this;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public OfferEntity setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public AgencyEntity getAgency() {
        return agency;
    }

    public OfferEntity setAgency(AgencyEntity agency) {
        this.agency = agency;
        return this;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public OfferEntity setDeleted(boolean deleted) {
        isDeleted = deleted;
        return this;
    }

    public RatingEnum getRating() {
        return rating;
    }

    public OfferEntity setRating(RatingEnum rating) {
        this.rating = rating;
        return this;
    }
}
