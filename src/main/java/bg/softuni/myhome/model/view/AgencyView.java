package bg.softuni.myhome.model.view;



public class AgencyView {

    private long id;
    private String name;
    private String address;
    private String phoneNumber;
    private String logoUrl;
    private int numberOfOffers = 0;

    public int getNumberOfOffers() {
        return numberOfOffers;
    }

    public AgencyView setNumberOfOffers(int numberOfOffers) {
        this.numberOfOffers = numberOfOffers;
        return this;
    }

    public long getId() {
        return id;
    }

    public AgencyView setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AgencyView setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public AgencyView setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public AgencyView setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public AgencyView setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
        return this;
    }
}
