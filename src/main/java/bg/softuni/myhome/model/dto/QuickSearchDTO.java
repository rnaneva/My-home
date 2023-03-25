package bg.softuni.myhome.model.dto;

import bg.softuni.myhome.model.enums.OfferTypeEnum;
import jakarta.validation.constraints.NotNull;


public class QuickSearchDTO {


    @NotNull(message = "Type of offer is required")
    private OfferTypeEnum type;

    @NotNull(message = "Category of property is required")
    private String category;

    @NotNull(message = "Location of property is required")
    private String city;


    private long userId;



    public OfferTypeEnum getType() {
        return type;
    }

    public QuickSearchDTO setType(OfferTypeEnum type) {
        this.type = type;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public QuickSearchDTO setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getCity() {
        return city;
    }

    public QuickSearchDTO setCity(String city) {
        this.city = city;
        return this;
    }


    public long getUserId() {
        return userId;
    }

    public QuickSearchDTO setUserId(long userId) {
        this.userId = userId;
        return this;
    }
}


