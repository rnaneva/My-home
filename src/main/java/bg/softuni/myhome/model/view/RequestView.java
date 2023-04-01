package bg.softuni.myhome.model.view;

import bg.softuni.myhome.model.enums.RequestStatusEnum;


public class RequestView {

    private long id;

    private String clientName;

    private String email;

    private String phone;

    private String message;

    private String notes;

    private RequestStatusEnum status;

    private String receivedOn;

    private String offerVisibleId;

    private String offerName;

    public String getOfferVisibleId() {
        return offerVisibleId;
    }

    public RequestView setOfferVisibleId(String offerVisibleId) {
        this.offerVisibleId = offerVisibleId;
        return this;
    }

    public String getOfferName() {
        return offerName;
    }

    public RequestView setOfferName(String offerName) {
        this.offerName = offerName;
        return this;
    }

    public String getReceivedOn() {
        return receivedOn;
    }

    public RequestView setReceivedOn(String receivedOn) {
        this.receivedOn = receivedOn;
        return this;
    }

    public long getId() {
        return id;
    }

    public RequestView setId(long id) {
        this.id = id;
        return this;
    }

    public String getClientName() {
        return clientName;
    }

    public RequestView setClientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RequestView setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public RequestView setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public RequestView setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public RequestView setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public RequestStatusEnum getStatus() {
        return status;
    }

    public RequestView setStatus(RequestStatusEnum status) {
        this.status = status;
        return this;
    }
}
