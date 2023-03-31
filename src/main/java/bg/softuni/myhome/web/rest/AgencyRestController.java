package bg.softuni.myhome.web.rest;


import bg.softuni.myhome.model.enums.StatusEnum;
import bg.softuni.myhome.model.view.OfferAgencyView;
import bg.softuni.myhome.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.NoPermissionException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class AgencyRestController {
    private final OfferService offerService;


    @Autowired
    public AgencyRestController(OfferService offerService) {
        this.offerService = offerService;

    }


    @GetMapping("/offers/active/{id}")
    public ResponseEntity<List<OfferAgencyView>> getOffers(@PathVariable("id") String userVisibleId) throws NoPermissionException {
        List<OfferAgencyView> offers =
                offerService.getOffersAgencyViewByStatus(userVisibleId, StatusEnum.ACTIVE);

        return ResponseEntity.ok(offers);
    }



}
