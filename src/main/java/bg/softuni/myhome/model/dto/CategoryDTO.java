package bg.softuni.myhome.model.dto;

public class CategoryDTO {

    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public CategoryDTO setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CategoryDTO setName(String name) {
        this.name = name;
        return this;
    }
}
