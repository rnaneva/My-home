package bg.softuni.myhome.web;

import bg.softuni.myhome.model.AppUserDetails;
import bg.softuni.myhome.model.dto.OfferPageOneDTO;
import bg.softuni.myhome.model.dto.OfferPageThreeDTO;
import bg.softuni.myhome.model.dto.OfferPageTwoDTO;
import bg.softuni.myhome.model.entities.OfferPageOne;
import bg.softuni.myhome.model.entities.OfferPageTwo;
import bg.softuni.myhome.model.entities.PictureEntity;
import bg.softuni.myhome.model.view.OfferPageOneView;
import bg.softuni.myhome.model.view.OfferPageTwoView;
import bg.softuni.myhome.model.view.PictureView;
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
public class OfferEditController {


    private final static String REDIRECT_PAGE_TWO = "redirect:/agency/offers/edit/two/";
    private final static String REDIRECT_PAGE_THREE = "redirect:/agency/offers/edit/three/";
    private final static String REDIRECT_PAGE_ONE = "redirect:/agency/offers/edit/one/";


    private OfferPageOneService offerPageOneService;
    private OfferService offerService;
    private CategoryService categoryService;
    private CityService cityService;
    private OfferPageTwoService offerPageTwoService;
    private PictureService pictureService;

    public OfferEditController(OfferPageOneService offerPageOneService, OfferService offerService,
                               CategoryService categoryService, CityService cityService,
                               OfferPageTwoService offerPageTwoService, PictureService pictureService) {
        this.offerPageOneService = offerPageOneService;
        this.offerService = offerService;
        this.categoryService = categoryService;
        this.cityService = cityService;
        this.offerPageTwoService = offerPageTwoService;

        this.pictureService = pictureService;
    }


    @GetMapping("/edit/one/{offerId}")
    public String getAddOfferPageOne(@PathVariable("offerId") String offerVisibleId,
                                     Model model,
                                     @AuthenticationPrincipal AppUserDetails appUserDetails) throws NoPermissionException {


        List<String> allCategoryNames = categoryService.getAllCategoryNames();
        model.addAttribute("categories", allCategoryNames);
        model.addAttribute("offerVisibleId", offerVisibleId);

        OfferPageOneView offerPageOne =
                offerPageOneService.getOfferPageOneViewByVisibleId(offerVisibleId);
        model.addAttribute("offerPageOne", offerPageOne);



        return "edit-offer-one";
    }

    @PutMapping("/edit/one/{offerId}")
    public String postAddOfferPageOne(@PathVariable("offerId") String offerVisibleId,
                                      @Valid OfferPageOneDTO offerPageOneDTO,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) throws NoPermissionException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("offerPageOneDTO", offerPageOneDTO)
                    .addFlashAttribute(BINDING_RESULT + "offerPageOneDTO", bindingResult);


            return REDIRECT_PAGE_ONE + offerVisibleId;

        }

        OfferPageOne offerPageOne =
                offerPageOneService.getOfferPageOneByOfferVisibleId(offerVisibleId);
        offerPageOneService.editOfferPageOne(offerPageOne, offerPageOneDTO);

        return REDIRECT_PAGE_TWO + offerVisibleId;
    }


    @GetMapping("/edit/two/{offerId}")
    public String getAddOfferPageTwo(@PathVariable("offerId") String offerVisibleId,
                                     Model model,
                                     @AuthenticationPrincipal AppUserDetails appUserDetails) throws NoPermissionException {

        List<String> allCityNames = cityService.getAllCityNames();
        model.addAttribute("cities", allCityNames);
        model.addAttribute("offerVisibleId", offerVisibleId);

        OfferPageTwoView offerPageTwo =
                offerPageTwoService.getOfferPageTwoViewByVisibleId(offerVisibleId);

        model.addAttribute("offerPageTwo", offerPageTwo);


        return "edit-offer-two";
    }

    @PutMapping("/edit/two/{offerId}")
    public String postAddOfferPageTwo(@PathVariable("offerId") String offerVisibleId,
                                      @Valid OfferPageTwoDTO offerPageTwoDTO,
                                      BindingResult bindingResult,

                                      RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("offerPageTwoDTO", offerPageTwoDTO)
                    .addFlashAttribute(BINDING_RESULT + "offerPageTwoDTO", bindingResult);


            return REDIRECT_PAGE_TWO + offerVisibleId;

        }

        OfferPageTwo pageTwo = offerPageTwoService.getOfferPageTwoByOfferVisibleId(offerVisibleId);

        offerPageTwoService.editPageTwo(pageTwo, offerPageTwoDTO);

        return REDIRECT_PAGE_THREE + offerVisibleId;
    }


    @GetMapping("/edit/three/{offerId}")
    public String getAddOfferPage3(@PathVariable("offerId") String offerVisibleId,
                                   Model model
    ) throws NoPermissionException {

        model.addAttribute("offerVisibleId", offerVisibleId);
        List<PictureView> pictures =
                offerService.getOfferPicturesByVisibleId(offerVisibleId);
        model.addAttribute("pictures", pictures);

        return "edit-offer-three";
    }

    @PostMapping("/edit/three/{offerId}")
    public String postAddOfferPageThree(@PathVariable("offerId") String offerVisibleId,
                                        @ModelAttribute("offerPageThreeDTO") OfferPageThreeDTO offerPageThreeDTO,
                                        @AuthenticationPrincipal AppUserDetails appUserDetails) {

        pictureService.savePictures(offerPageThreeDTO, offerVisibleId);

        return REDIRECT_PAGE_THREE + offerVisibleId;
    }


    private static void authorize(String userVisibleId, AppUserDetails appUserDetails) throws NoPermissionException {
        if (!appUserDetails.getVisibleId().equals(userVisibleId)) {
            throw new NoPermissionException();
        }
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
