package bg.softuni.myhome.web;

import bg.softuni.myhome.model.AppUserDetails;
import bg.softuni.myhome.model.dto.OfferPageOneDTO;
import bg.softuni.myhome.model.dto.OfferPageThreeDTO;
import bg.softuni.myhome.model.dto.OfferPageTwoDTO;
import bg.softuni.myhome.model.entities.OfferPageOne;
import bg.softuni.myhome.model.entities.OfferPageTwo;
import bg.softuni.myhome.service.*;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.NoPermissionException;

import static bg.softuni.myhome.commons.StaticVariables.*;

@Controller
@RequestMapping("/agency/offers")
public class OfferAddController {


    private final OfferPageOneService offerPageOneService;
    private final OfferService offerService;
    private final OfferPageTwoService offerPageTwoService;
    private final PictureService pictureService;

    public OfferAddController(OfferPageOneService offerPageOneService, OfferService offerService,
                              OfferPageTwoService offerPageTwoService, PictureService pictureService) {
        this.offerPageOneService = offerPageOneService;
        this.offerService = offerService;

        this.offerPageTwoService = offerPageTwoService;
        this.pictureService = pictureService;
    }


    @GetMapping("/add/one")
    public String getAddOfferPageOne(
            @AuthenticationPrincipal AppUserDetails appUserDetails,
            Model model) {

        offerPageOneService.addAttributesToModel(model);
        return "offers/add-offer-one";
    }

    @PostMapping("/add/one")
    public String postAddOfferPageOne(@AuthenticationPrincipal AppUserDetails appUserDetails,
                                      @Valid OfferPageOneDTO offerPageOneDTO,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) {
        String visibleId = appUserDetails.getVisibleId();
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("offerPageOneDTO", offerPageOneDTO)
                    .addFlashAttribute(BINDING_RESULT + "offerPageOneDTO", bindingResult);


            return REDIRECT_ADD_PAGE_ONE;

        }

        OfferPageOne pageOne = offerPageOneService.saveOfferPageOne(offerPageOneDTO);
        String offerId = offerService.createOfferWithPageOne(pageOne, visibleId).getVisibleId();

        return REDIRECT_ADD_PAGE_TWO + offerId;
    }


    @GetMapping("/add/two/{offerId}")
    public String getAddOfferPageTwo(@PathVariable("offerId") String offerVisibleId,
                                     Model model,
                                     @AuthenticationPrincipal AppUserDetails appUserDetails) throws NoPermissionException {

        offerPageTwoService.addAttributesToModel(model, offerVisibleId);

        return "offers/add-offer-two";
    }

    @PostMapping("/add/two/{offerId}")
    public String postAddOfferPageTwo(@PathVariable("offerId") String offerVisibleId,
                                      @Valid OfferPageTwoDTO offerPageTwoDTO,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("offerPageTwoDTO", offerPageTwoDTO)
                    .addFlashAttribute(BINDING_RESULT + "offerPageTwoDTO", bindingResult);

            return REDIRECT_ADD_PAGE_TWO + offerVisibleId;
        }

        OfferPageTwo pageTwo =
                offerPageTwoService.savePageTwo(offerPageTwoDTO);
        offerService.addPageTwoToOffer(pageTwo, offerVisibleId);

        return REDIRECT_EDIT_PAGE_THREE + offerVisibleId;
    }


    @GetMapping("/add/three/{offerId}")
    public String getAddOfferPage3(@PathVariable("offerId") String offerVisibleId,
                                   Model model) {

        model.addAttribute("offerVisibleId", offerVisibleId);

        return "offers/add-offer-three";
    }

    @PostMapping("/add/three/{offerId}")
    public String postAddOfferPageThree(@PathVariable("offerId") String offerVisibleId,
                                        @Valid OfferPageThreeDTO offerPageThreeDTO,
                                        BindingResult bindingResult,
                                        RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("offerPageThreeDTO", offerPageThreeDTO)
                    .addFlashAttribute(BINDING_RESULT + "offerPageThreeDTO", bindingResult);

            return REDIRECT_ADD_PAGE_THREE + offerVisibleId;
        }

        pictureService.savePictures(offerPageThreeDTO, offerVisibleId);

        return REDIRECT_EDIT_PAGE_THREE + offerVisibleId;
    }


    @ModelAttribute
    public OfferPageOneDTO offerPageOneDTO() {
        return new OfferPageOneDTO();
    }

    @ModelAttribute
    public OfferPageTwoDTO offerPageTwoDTO() {
        return new OfferPageTwoDTO();
    }

    @ModelAttribute
    public OfferPageThreeDTO offerPageThreeDTO() {
        return new OfferPageThreeDTO();
    }
}
