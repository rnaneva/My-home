package bg.softuni.myhome.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "pictures")
public class PictureEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String title;

    @Column(nullable = false)
    private String url;

    @ManyToOne(cascade = CascadeType.DETACH)
    private OfferEntity offer;

    public Long getId() {
        return id;
    }

    public PictureEntity setId(Long id) {
        this.id = id;
        return this;
    }

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
