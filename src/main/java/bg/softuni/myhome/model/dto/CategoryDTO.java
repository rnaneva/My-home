package bg.softuni.myhome.model.dto;

import bg.softuni.myhome.model.validation.annotations.UniqueCategory;
import jakarta.validation.constraints.NotBlank;

public class  CategoryDTO {

    @NotBlank(message = "Name of the category is required")
    @UniqueCategory
    private String name;

    public String getName() {
        return name;
    }

    public CategoryDTO setName(String name) {
        this.name = name;
        return this;
    }
}
