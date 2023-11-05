package bg.softuni.myhome.web.rest;

import bg.softuni.myhome.model.AppUserDetails;
import bg.softuni.myhome.model.view.OfferView;
import bg.softuni.myhome.service.OfferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("/offers/**")
public class OffersRestController {

    private final OfferService offerService;

    public OffersRestController(OfferService offerService) {
        this.offerService = offerService;
    }

    @Operation(summary = "Returns 0 or all favourite offers to logged user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = OfferView.class))}),
            @ApiResponse(responseCode = "300", description = "Id the user is not logged in")
    })
    @GetMapping("/favourites")
    public ResponseEntity<List<OfferView>> getFavouriteOffers(@AuthenticationPrincipal AppUserDetails appUserDetails) {
        List<OfferView> favouriteOffers = offerService.getFavouriteOffersForUser(appUserDetails);
        return ResponseEntity.ok(favouriteOffers);
    }

    @Operation(summary = "Returns 0 or all available active offers for rent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content ={@Content(mediaType = "application/json",
                    schema = @Schema(implementation = OfferView.class))})
    })
    @GetMapping("/rent")
    public ResponseEntity<List<OfferView>> getRentOffers() {
        List<OfferView> rentOffers = offerService.allRentProperties();
        return ResponseEntity.ok(rentOffers);
    }

    @Operation(summary = "Returns 0 or all available active offers for sale")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OfferView.class))})
    })
    @GetMapping("/sale")
    public ResponseEntity<List<OfferView>> getSaleOffers() {
        List<OfferView> saleOffers = offerService.allSaleProperties();
        return ResponseEntity.ok(saleOffers);

    }

    @Operation(summary = "Returns 0 or all available active offers by agency id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OfferView.class))})
    })
    @GetMapping("/ag/{name}")
    public ResponseEntity<List<OfferView>> getAllOffersByAgencyId(@PathVariable String name) {
        List<OfferView> offers = offerService.getOffersByAgencyName(name);
        return ResponseEntity.ok(offers);
    }


}
