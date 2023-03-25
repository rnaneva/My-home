package bg.softuni.myhome.model.dto;


import java.math.BigDecimal;

public class OfferDTO {

    private String name;
    private String imageURL;
    private String address;
    private BigDecimal area;
    private int floor;
    private int allFloors;
    private int bedrooms;
    private int bathrooms;
    private int balconies;
    private String description;
    private BigDecimal price;

    public String getAddress() {
        return address;
    }

    public OfferDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getName() {
        return name;
    }

    public OfferDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageURL() {
        return imageURL;
    }

    public OfferDTO setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }


    public BigDecimal getArea() {
        return area;
    }

    public OfferDTO setArea(BigDecimal area) {
        this.area = area;
        return this;
    }

    public int getFloor() {
        return floor;
    }

    public OfferDTO setFloor(int floor) {
        this.floor = floor;
        return this;
    }

    public int getAllFloors() {
        return allFloors;
    }

    public OfferDTO setAllFloors(int allFloors) {
        this.allFloors = allFloors;
        return this;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public OfferDTO setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
        return this;
    }

    public int getBathrooms() {
        return bathrooms;
    }

    public OfferDTO setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
        return this;
    }

    public int getBalconies() {
        return balconies;
    }

    public OfferDTO setBalconies(int balconies) {
        this.balconies = balconies;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OfferDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OfferDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
