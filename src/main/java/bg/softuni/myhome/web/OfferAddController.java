package bg.softuni.myhome.web;

import bg.softuni.myhome.model.AppUserDetails;
import bg.softuni.myhome.model.dto.OfferAddPageOneDTO;
import bg.softuni.myhome.model.dto.OfferAddPageThreeDTO;
import bg.softuni.myhome.model.dto.OfferAddPageTwoDTO;
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

import static bg.softuni.myhome.commons.StaticVariables.BINDING_RESULT;

@Controller
@RequestMapping("/agency/offers")
public class OfferAddController {

    private final static String REDIRECT_PAGE_TWO = "redirect:/agency/offers/add/two/";
    private final static String REDIRECT_PAGE_THREE = "redirect:/agency/offers/add/three/";
    private final static String REDIRECT_PAGE_ONE = "redirect:/agency/offers/add/one/";


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
                                     @AuthenticationPrincipal AppUserDetails appUserDetails) throws NoPermissionException {

        authorize(userVisibleId, appUserDetails);

        List<String> allCategoryNames = categoryService.getAllCategoryNames();
        model.addAttribute("categories", allCategoryNames);


        return "add-offer-one";
    }

    @PostMapping("/add/one/{id}")
    public String postAddOfferPageOne(@PathVariable("id") String userVisibleId,
                                      @Valid @ModelAttribute("offerAddPageOneDTO") OfferAddPageOneDTO offerAddPageOneDTO,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) throws NoPermissionException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("offerAddPageOneDTO", offerAddPageOneDTO)
                    .addFlashAttribute(BINDING_RESULT + "offerAddPageOneDTO", bindingResult);


            return REDIRECT_PAGE_ONE + userVisibleId;

        }

        OfferPageOne pageOne = offerPageOneService.saveOfferPageOne(offerAddPageOneDTO);
        String offerId = offerService.createOfferWithPageOne(pageOne, userVisibleId).getVisibleId();


        return REDIRECT_PAGE_TWO + offerId;
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
                                      Model model,
                                      @Valid @ModelAttribute("offerAddPageTwoDTO") OfferAddPageTwoDTO offerAddPageTwoDTO,
                                      BindingResult bindingResult,

                                      RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("offerAddPageTwoDTO", offerAddPageTwoDTO)
                    .addFlashAttribute(BINDING_RESULT + "offerAddPageTwoDTO", bindingResult);


            return REDIRECT_PAGE_TWO + offerVisibleId;

        }


        OfferPageTwo pageTwo =
                offerPageTwoService.savePageTwo(offerAddPageTwoDTO);
        offerService.addPageTwoToOffer(pageTwo, offerVisibleId);

        return REDIRECT_PAGE_THREE + offerVisibleId;
    }


    @GetMapping("/add/three/{offerId}")
    public String getAddOfferPage3(@PathVariable("offerId") String offerVisibleId,
                                   Model model
                                  ) throws NoPermissionException {

        model.addAttribute("offerVisibleId", offerVisibleId);

        return "add-offer-three";
    }

    @PostMapping("/add/three/{offerId}")
    public String postAddOfferPageThree(@PathVariable("offerId") String offerVisibleId,
                                        @ModelAttribute("offerAddPageThreeDTO") OfferAddPageThreeDTO offerAddPageThreeDTO,
                                        @AuthenticationPrincipal AppUserDetails appUserDetails) {

         pictureService.savePictures(offerAddPageThreeDTO, offerVisibleId);

        return "redirect:/agency/offers/inactive/" + appUserDetails.getVisibleId();
    }


    private static void authorize(String userVisibleId, AppUserDetails appUserDetails) throws NoPermissionException {
        if (!appUserDetails.getVisibleId().equals(userVisibleId)) {
            throw new NoPermissionException();
        }
    }

    @ModelAttribute
    public OfferAddPageOneDTO offerAddPageOneDTO() {
        return new OfferAddPageOneDTO();
    }

    @ModelAttribute
    public OfferAddPageTwoDTO offerAddPagetwoDTO() {
        return new OfferAddPageTwoDTO();
    }

    @ModelAttribute
    public OfferAddPageThreeDTO offerAddPageThreeDTO(){
        return new OfferAddPageThreeDTO();
    }
}
