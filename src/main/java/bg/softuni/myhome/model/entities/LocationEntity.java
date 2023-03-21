package bg.softuni.myhome.model.entities;

import jakarta.persistence.Entity;

import jakarta.persistence.Table;


@Entity
@Table(name = "cities")
public class LocationEntity extends BaseEntity{

    private String city;


    public String getCity() {
        return city;
    }

    public LocationEntity setCity(String city) {
        this.city = city;
        return this;
    }


}