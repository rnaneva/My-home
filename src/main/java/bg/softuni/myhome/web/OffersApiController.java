package bg.softuni.myhome.web;

import bg.softuni.myhome.model.dto.OfferDTO;
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
    public ResponseEntity<List<OfferDTO>> getAllRentProperties() {
        return ResponseEntity.ok(offerService.allRentProperties());
    }




    @GetMapping("/sale")
    public ResponseEntity<List<OfferDTO>> getAllSaleProperties() {
        return ResponseEntity.ok(offerService.allSaleProperties());
    }
}
