package bg.softuni.myhome.model.view;


import java.math.BigDecimal;

public class OfferView {

//    todo reminder - 50 character for name
    private String name;
    private String imageURL;
    private String address;
    private BigDecimal area;
    private String floorInfo;
    private String plan;
    private String description;
    private BigDecimal price;
    private String city;
    private String visibleId;

    public String getVisibleId() {
        return visibleId;
    }

    public OfferView setVisibleId(String visibleId) {
        this.visibleId = visibleId;
        return this;
    }

    public String getCity() {
        return city;
    }


    public OfferView setCity(String city) {
        this.city = city;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public OfferView setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getName() {
        return name;
    }

    public OfferView setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageURL() {
        return imageURL;
    }

    public OfferView setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }


    public BigDecimal getArea() {
        return area;
    }

    public OfferView setArea(BigDecimal area) {
        this.area = area;
        return this;
    }

    public String getFloorInfo() {
        return floorInfo;
    }

    public OfferView setFloorInfo(String floorInfo) {
        this.floorInfo = floorInfo;
        return this;
    }

    public String getPlan() {
        return plan;
    }

    public OfferView setPlan(String plan) {
        this.plan = plan;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OfferView setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OfferView setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
