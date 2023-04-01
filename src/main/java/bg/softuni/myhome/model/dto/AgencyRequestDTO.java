package bg.softuni.myhome.model.dto;

import bg.softuni.myhome.model.enums.RequestStatusEnum;

public class AgencyRequestDTO {

    private RequestStatusEnum status;
    private String notes;

    public RequestStatusEnum getStatus() {
        return status;
    }

    public AgencyRequestDTO setStatus(RequestStatusEnum status) {
        this.status = status;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public AgencyRequestDTO setNotes(String notes) {
        this.notes = notes;
        return this;
    }
}
