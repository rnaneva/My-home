package bg.softuni.myhome.model.dto;

import bg.softuni.myhome.model.validation.annotations.UniqueCity;
import jakarta.validation.constraints.NotBlank;

public class CityDTO {
    private String name;

    @NotBlank(message = "Name of the city is required")
    @UniqueCity
    public String getName() {
        return name;
    }

    public CityDTO setName(String name) {
        this.name = name;
        return this;
    }
}
