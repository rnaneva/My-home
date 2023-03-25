package bg.softuni.myhome.model.dto;

public class CityDTO {

    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public CityDTO setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CityDTO setName(String name) {
        this.name = name;
        return this;
    }
}
