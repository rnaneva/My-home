package bg.softuni.myhome.web.rest;

import bg.softuni.myhome.model.view.OfferAgencyView;
import bg.softuni.myhome.model.view.PictureView;
import bg.softuni.myhome.service.OfferService;
import bg.softuni.myhome.service.PictureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
@CrossOrigin("/agency/**")
public class PictureRestController {

    private PictureService pictureService;
    private OfferService offerService;

    public PictureRestController(PictureService pictureService, OfferService offerService) {
        this.pictureService = pictureService;
        this.offerService = offerService;
    }


    @GetMapping("/{id}/pictures")
    public ResponseEntity<List<PictureView>> getOfferImages(@PathVariable("id") String offerId)  {
        List<PictureView> pictures = offerService.getOfferPicturesByVisibleId(offerId);
        return ResponseEntity.ok(pictures);
    }


    @GetMapping("/{id}/pictures/{imgId}")
    public ResponseEntity<PictureView> getImage(@PathVariable("id") String offerId,
                                                       @PathVariable("imgId") long id )
             {

                 PictureView picture = pictureService.getPictureViewById(id);


                 return ResponseEntity.ok(picture);
    }

//    todo - not getting here
    @DeleteMapping("/{id}/pictures/{imgId}")
    public ResponseEntity<PictureView> deletePicture(@PathVariable("id") String offerId,
                                                               @PathVariable("imgId") long id) {
        boolean isDeleted = pictureService.deleteById(id);


        return isDeleted ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }

}
