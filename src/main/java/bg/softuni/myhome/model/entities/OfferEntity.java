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
public class    OfferEntity extends BaseEntity {

    @OneToOne(fetch = FetchType.EAGER)
    private OfferPageOne offerPageOne;

    @OneToOne(fetch = FetchType.EAGER)
    private OfferPageTwo offerPageTwo;

//    Page 3

    @OneToMany(mappedBy = "offer")
    private List<PictureEntity> pictures;


//

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Column(nullable = false)
    private String visibleId;

    @DateTimeFormat(pattern = "dd-MM-yy")
    @Column(nullable = false)
    private LocalDate createdOn;

    @ManyToOne
    private AgencyEntity agency;



//    todo rating
    @Enumerated(EnumType.STRING)
    private RatingEnum rating;

    public String coverPhoto(){
        return !this.pictures.isEmpty() ? this.getPictures().get(0).getUrl()
                : "https://res.cloudinary.com/dipiksmcm/image/upload/v1680251091/o1tqjxwnm0vhq2tao3er.jpg";
    }

    public String fullAddress(){
        return this.offerPageTwo.getLocation().getCity().getName() + ", "
                + this.getOfferPageTwo().getLocation().getAddress();
    }

    public String plan(){
        StringBuilder plan = new StringBuilder();
        if(this.offerPageTwo.getBedrooms() == 1){
            plan.append("1 bedroom, ");
        } else{
            plan.append(this.offerPageTwo.getBedrooms()).append(" bedrooms, ");
        }

        if(this.offerPageTwo.getBathrooms() == 1){
            plan.append("1 bathroom, ");
        } else{
            plan.append(this.offerPageTwo.getBathrooms()).append(" bathrooms, ");
        }

        if(this.offerPageTwo.getBalconies() == 1){
            plan.append("1 balcony");
        } else{
            plan.append(this.offerPageTwo.getBalconies()).append(" balconies");
        }

        return plan.toString().trim();
    }

    public String floorInfo(){
        return String.format("%d from %d",
                this.offerPageTwo.getFloor(),
                this.getOfferPageTwo().getAllFloors());
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public OfferEntity setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public OfferEntity() {
        this.pictures = new ArrayList<>();
    }

    public OfferPageOne getOfferPageOne() {
        return offerPageOne;
    }

    public OfferEntity setOfferPageOne(OfferPageOne offerPageOne) {
        this.offerPageOne = offerPageOne;
        return this;
    }

    public OfferPageTwo getOfferPageTwo() {
        return offerPageTwo;
    }

    public OfferEntity setOfferPageTwo(OfferPageTwo offerPageTwo) {
        this.offerPageTwo = offerPageTwo;
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

    public String getVisibleId() {
        return visibleId;
    }

    public OfferEntity setVisibleId(String visibleId) {
        this.visibleId = visibleId;
        return this;
    }




    public AgencyEntity getAgency() {
        return agency;
    }

    public OfferEntity setAgency(AgencyEntity agency) {
        this.agency = agency;
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
