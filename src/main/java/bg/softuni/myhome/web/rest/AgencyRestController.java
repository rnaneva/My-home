package bg.softuni.myhome.web.rest;


import bg.softuni.myhome.model.entities.OfferEntity;
import bg.softuni.myhome.model.enums.RequestStatusEnum;
import bg.softuni.myhome.model.enums.StatusEnum;
import bg.softuni.myhome.model.view.OfferAgencyView;
import bg.softuni.myhome.model.view.OfferView;
import bg.softuni.myhome.model.view.RequestView;
import bg.softuni.myhome.service.OfferService;
import bg.softuni.myhome.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.naming.NoPermissionException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("/agency/**")
public class AgencyRestController {
    private final OfferService offerService;
    private final RequestService requestService;

    @Autowired
    public AgencyRestController(OfferService offerService, RequestService requestService) {
        this.offerService = offerService;
        this.requestService = requestService;
    }


    @GetMapping("/offers/active/{id}")
    public ResponseEntity<List<OfferAgencyView>> getActiveOffers(@PathVariable("id") String userVisibleId) throws NoPermissionException {
        List<OfferAgencyView> offers =
                offerService.getOffersAgencyViewByStatus(userVisibleId, StatusEnum.ACTIVE);

        return ResponseEntity.ok(offers);
    }

    @GetMapping("/offers/inactive/{id}")
    public ResponseEntity<List<OfferAgencyView>> getInactiveOffers(@PathVariable("id") String userVisibleId) throws NoPermissionException {
        List<OfferAgencyView> offers =
                offerService.getOffersAgencyViewByStatus(userVisibleId, StatusEnum.INACTIVE);

        return ResponseEntity.ok(offers);
    }


    @GetMapping("/requests/replied/{id}")
    public ResponseEntity<List<RequestView>> getRepliedRequests(@PathVariable("id") String userVisibleId) {
        List<RequestView> requests =
                requestService.findRequestViewsByAgencyIdAndStatus(userVisibleId, RequestStatusEnum.REPLIED);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/requests/new/{id}")
    public ResponseEntity<List<RequestView>> getNewRequests(@PathVariable("id") String userVisibleId) {
        List<RequestView> requests =
                requestService.findRequestViewsByAgencyIdAndStatus(userVisibleId, RequestStatusEnum.NEW);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/requests/rejected/{id}")
    public ResponseEntity<List<RequestView>> getRejectedRequests(@PathVariable("id") String userVisibleId) {
        List<RequestView> requests =
                requestService.findRequestViewsByAgencyIdAndStatus(userVisibleId, RequestStatusEnum.REJECT);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/requests/inspection/{id}")
    public ResponseEntity<List<RequestView>> getRequestsForInspection(@PathVariable("id") String userVisibleId) {
        List<RequestView> requests =
                requestService.findRequestViewsByAgencyIdAndStatus(userVisibleId, RequestStatusEnum.INSPECTION);
        return ResponseEntity.ok(requests);
    }


    @GetMapping("/offers/inactive/{id}/activate")
    public ResponseEntity<OfferAgencyView> activateOffer(@PathVariable("id") String offerId) {

        OfferEntity offer = offerService.getOfferByVisibleId(offerId);
//         todo handle error for offer without pics == null
        offerService.changeOfferStatus(offer, StatusEnum.ACTIVE);
        return ResponseEntity.ok().build();

    }


    @GetMapping("/offers/active/{id}/deactivate")
    public ResponseEntity<OfferAgencyView> deactivateOffer(@PathVariable("id") String offerId) {

        OfferEntity offer = offerService.getOfferByVisibleId(offerId);

        offerService.changeOfferStatus(offer, StatusEnum.INACTIVE);
        return ResponseEntity.ok().build();

    }



}
