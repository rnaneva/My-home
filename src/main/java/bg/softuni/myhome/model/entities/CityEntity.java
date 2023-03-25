package bg.softuni.myhome.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "cities")
public class CityEntity extends BaseEntity{

    @Column(nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public CityEntity setName(String city) {
        this.name = city;
        return this;
    }
}
