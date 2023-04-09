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

import java.util.List;

import static bg.softuni.myhome.commons.StaticVariables.*;

@Controller
@RequestMapping("/agency/offers")
public class OfferAddController {


    private OfferPageOneService offerPageOneService;
    private OfferService offerService;
    private CategoryService categoryService;
    private CityService cityService;
    private OfferPageTwoService offerPageTwoService;
    private PictureService pictureService;

    public OfferAddController(OfferPageOneService offerPageOneService, OfferService offerService,
                              CategoryService categoryService, CityService cityService,
                              OfferPageTwoService offerPageTwoService, PictureService pictureService) {
        this.offerPageOneService = offerPageOneService;
        this.offerService = offerService;
        this.categoryService = categoryService;
        this.cityService = cityService;
        this.offerPageTwoService = offerPageTwoService;

        this.pictureService = pictureService;
    }


    @GetMapping("/add/one/{id}")
    public String getAddOfferPageOne(@PathVariable("id") String userVisibleId,
                                     Model model,
                                     @AuthenticationPrincipal AppUserDetails appUserDetails) {


        List<String> allCategoryNames = categoryService.getAllCategoryNames();
        model.addAttribute("categories", allCategoryNames);


        return "add-offer-one";
    }

    @PostMapping("/add/one/{id}")
    public String postAddOfferPageOne(@PathVariable("id") String userVisibleId,
                                      @Valid OfferPageOneDTO offerPageOneDTO,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("offerAddPageOneDTO", offerPageOneDTO)
                    .addFlashAttribute(BINDING_RESULT + "offerPageOneDTO", bindingResult);


            return REDIRECT_ADD_PAGE_ONE + userVisibleId;

        }

        OfferPageOne pageOne = offerPageOneService.saveOfferPageOne(offerPageOneDTO);
        String offerId = offerService.createOfferWithPageOne(pageOne, userVisibleId).getVisibleId();


        return REDIRECT_ADD_PAGE_TWO + offerId;
    }


    @GetMapping("/add/two/{offerId}")
    public String getAddOfferPageTwo(@PathVariable("offerId") String offerVisibleId,
                                     Model model,
                                     @AuthenticationPrincipal AppUserDetails appUserDetails) throws NoPermissionException {

        List<String> allCityNames = cityService.getAllCityNames();
        model.addAttribute("cities", allCityNames);
        model.addAttribute("offerVisibleId", offerVisibleId);


        return "add-offer-two";
    }

    @PostMapping("/add/two/{offerId}")
    public String postAddOfferPageTwo(@PathVariable("offerId") String offerVisibleId,
                                      @Valid OfferPageTwoDTO offerPageTwoDTO,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("offerAddPageTwoDTO", offerPageTwoDTO)
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

        return "add-offer-three";
    }

    @PostMapping("/add/three/{offerId}")
    public String postAddOfferPageThree(@PathVariable("offerId") String offerVisibleId,
                                        @Valid OfferPageThreeDTO offerPageThreeDTO,
                                        BindingResult bindingResult,
                                        RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("offerPageThreeDTO", offerPageThreeDTO)
                    .addFlashAttribute(BINDING_RESULT + "offerPageThreeDTO", bindingResult );

            return REDIRECT_ADD_PAGE_THREE + offerVisibleId;
        }

        pictureService.savePictures(offerPageThreeDTO, offerVisibleId);

        return REDIRECT_EDIT_PAGE_THREE + offerVisibleId;
    }


    @ModelAttribute
    public OfferPageOneDTO offerAddPageOneDTO() {
        return new OfferPageOneDTO();
    }

    @ModelAttribute
    public OfferPageTwoDTO offerAddPageTwoDTO() {
        return new OfferPageTwoDTO();
    }

    @ModelAttribute
    public OfferPageThreeDTO offerAddPageThreeDTO() {
        return new OfferPageThreeDTO();
    }
}
