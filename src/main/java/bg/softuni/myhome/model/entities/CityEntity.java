package bg.softuni.myhome.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "cities")
public class CityEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String name;

    public CityEntity(){}

    public Long getId() {
        return id;
    }

    public CityEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CityEntity setName(String city) {
        this.name = city;
        return this;
    }
}
