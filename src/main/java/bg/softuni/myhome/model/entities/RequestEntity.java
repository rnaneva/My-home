package bg.softuni.myhome.model.entities;

import bg.softuni.myhome.model.enums.RequestStatusEnum;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "requests")
public class RequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate receivedOn;

    @ManyToOne(optional = false)
    private OfferEntity offer;

    @Column(nullable = false)
    private String clientName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(columnDefinition = "LONGTEXT")
    private String message;

    @Column(columnDefinition = "LONGTEXT")
    private String notes;

    @Enumerated(EnumType.STRING)
    private RequestStatusEnum status;

    public RequestEntity() {
        this.receivedOn = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public RequestEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getReceivedOn() {
        return receivedOn;
    }

    public RequestEntity setReceivedOn(LocalDate receivedOn) {
        this.receivedOn = receivedOn;
        return this;
    }




    public String getClientName() {
        return clientName;
    }

    public RequestEntity setClientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RequestEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public RequestEntity setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public RequestEntity setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public RequestEntity setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public RequestStatusEnum getStatus() {
        return status;
    }

    public RequestEntity setStatus(RequestStatusEnum status) {
        this.status = status;
        return this;
    }

    public OfferEntity getOffer() {
        return offer;
    }

    public RequestEntity setOffer(OfferEntity offer) {
        this.offer = offer;
        return this;
    }
}
