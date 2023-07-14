package bg.softuni.myhome.web.rest;

import bg.softuni.myhome.model.view.PictureView;
import bg.softuni.myhome.service.OfferService;
import bg.softuni.myhome.service.PictureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
@CrossOrigin("/agency/**")
public class PictureRestController {

    private final PictureService pictureService;
    private final OfferService offerService;

    public PictureRestController(PictureService pictureService, OfferService offerService) {
        this.pictureService = pictureService;
        this.offerService = offerService;
    }


    @Operation(summary = "Returns 0 or all pictures by offer ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "If the offer ID exists.",
                    content = {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = PictureView.class))}),
            @ApiResponse(responseCode = "400", description = "If the offer ID does not exist.")
    })
    @GetMapping("/{id}/pictures")
    public ResponseEntity<List<PictureView>> getOfferImages(@PathVariable("id") String offerId) {
        List<PictureView> pictures = offerService.getOfferPicturesByVisibleId(offerId);
        return ResponseEntity.ok(pictures);
    }


    @Operation(summary = "Returns an image by image ID and offer ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "If the offer ID and image ID exist.",
                    content = {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = PictureView.class))}),
            @ApiResponse(responseCode = "400", description = "If the offer ID and/or image ID do not exist.")
    })
    @GetMapping("/{id}/pictures/{imgId}")
    public ResponseEntity<PictureView> getImage(@PathVariable("id") String offerId,
                                                @PathVariable("imgId") long id) {

        PictureView picture = pictureService.getPictureViewById(id);
        return ResponseEntity.ok(picture);
    }


    @Operation(summary = "Deletes an image by image ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "If the image ID exists.",
                    content = {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = PictureView.class))}),
            @ApiResponse(responseCode = "404", description = "If the image ID does not exist")
    })
    @DeleteMapping("/{id}/pictures/{imgId}")
    public ResponseEntity<PictureView> deletePicture(@PathVariable("id") String offerId,
                                                     @PathVariable("imgId") long id) {
        boolean isDeleted = pictureService.deleteById(id);

        return isDeleted ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }

}
