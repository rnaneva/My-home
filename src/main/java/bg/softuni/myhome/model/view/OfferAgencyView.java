package bg.softuni.myhome.model.view;

import bg.softuni.myhome.model.enums.StatusEnum;

import java.util.UUID;

public class OfferAgencyView {

    private String visibleId;
    private StatusEnum status;
    private String offerPageOneName;
    private String createdOn;

    public String getVisibleId() {
        return visibleId;
    }

    public OfferAgencyView setVisibleId(String visibleId) {
        this.visibleId = visibleId;
        return this;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public OfferAgencyView setStatus(StatusEnum status) {
        this.status = status;
        return this;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public OfferAgencyView setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public String getOfferPageOneName() {
        return offerPageOneName;
    }

    public OfferAgencyView setOfferPageOneName(String offerPageOne) {
        this.offerPageOneName = offerPageOne;
        return this;
    }




}
