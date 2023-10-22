package bg.softuni.myhome.web.rest;

import bg.softuni.myhome.model.view.AgencyView;
import bg.softuni.myhome.model.view.OfferView;
import bg.softuni.myhome.service.AgencyService;
import bg.softuni.myhome.service.OfferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("/agencies/**")
public class AllAgenciesRestController {

    private final AgencyService agencyService;
    private final OfferService offerService;

    public AllAgenciesRestController(AgencyService agencyService, OfferService offerService) {
        this.agencyService = agencyService;
        this.offerService = offerService;
    }

    @Operation(summary = "Returns brief information for all registered agencies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AgencyView.class))})
    })
    @GetMapping("/api/agencies")
    public ResponseEntity<List<AgencyView>> getAllAgencies() {
        List<AgencyView> agencies = agencyService.getAllAgencies();
        return ResponseEntity.ok(agencies);
    }



    @Operation(summary = "Returns brief information for agencies, filtered by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AgencyView.class))})
    })

    @GetMapping("/api/agencies/{name}")
    public ResponseEntity<List<AgencyView>> getAgenciesByName(@PathVariable String name) {
        List<AgencyView> agencies = agencyService.getAgenciesViewByName(name);

        return ResponseEntity.ok(agencies);
    }



}
