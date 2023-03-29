package bg.softuni.myhome.web.rest;

import bg.softuni.myhome.model.view.OfferView;
import bg.softuni.myhome.service.OfferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("/offers")
@RestController
@RequestMapping("/api/offers")
public class OffersApiController {

    private final OfferService offerService;

    public OffersApiController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/rent")
    public ResponseEntity<List<OfferView>> getAllRentProperties() {
        return ResponseEntity.ok(offerService.allRentProperties());
    }




    @GetMapping("/sale")
    public ResponseEntity<List<OfferView>> getAllSaleProperties() {
        return ResponseEntity.ok(offerService.allSaleProperties());
    }
}
