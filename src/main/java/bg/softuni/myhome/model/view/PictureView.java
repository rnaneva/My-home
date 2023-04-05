package bg.softuni.myhome.model.view;

public class PictureView {
    private long id;
    private String url;
    private String name;

    public long getId() {
        return id;
    }

    public PictureView setId(long id) {
        this.id = id;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public PictureView setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getName() {
        return name;
    }

    public PictureView setName(String name) {
        this.name = name;
        return this;
    }
}
