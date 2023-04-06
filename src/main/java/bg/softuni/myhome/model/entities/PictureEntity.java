package bg.softuni.myhome.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "pictures")
public class PictureEntity extends BaseEntity {

    private String title;

    @Column(nullable = false)
    private String url;

    @ManyToOne(cascade = CascadeType.DETACH)
    private OfferEntity offer;

    public OfferEntity getOffer() {
        return offer;
    }

    public PictureEntity setOffer(OfferEntity offer) {
        this.offer = offer;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PictureEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public PictureEntity setUrl(String url) {
        this.url = url;
        return this;
    }
}
