package bg.softuni.myhome.model.view;

public class CityView {

    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public CityView setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CityView setName(String name) {
        this.name = name;
        return this;
    }
}
