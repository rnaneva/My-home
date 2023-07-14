package bg.softuni.myhome.web;


import bg.softuni.myhome.model.dto.OfferPageOneDTO;
import bg.softuni.myhome.model.dto.OfferPageThreeDTO;
import bg.softuni.myhome.model.dto.OfferPageTwoDTO;
import bg.softuni.myhome.model.entities.OfferPageOne;
import bg.softuni.myhome.model.entities.OfferPageTwo;
import bg.softuni.myhome.model.view.OfferPageOneView;
import bg.softuni.myhome.model.view.OfferPageTwoView;
import bg.softuni.myhome.model.view.PictureView;
import bg.softuni.myhome.service.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

import static bg.softuni.myhome.commons.StaticVariables.*;

@Controller
@RequestMapping("/agency/offers")
public class OfferEditController {

    private final OfferPageOneService offerPageOneService;
    private final OfferService offerService;
    private final CategoryService categoryService;
    private final CityService cityService;
    private final OfferPageTwoService offerPageTwoService;
    private final PictureService pictureService;

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
    public String getEditOfferPageOne(@PathVariable("offerId") String offerVisibleId,
                                     Model model) {


        List<String> allCategoryNames = categoryService.getAllCategoryNames();
        model.addAttribute("categories", allCategoryNames);
        model.addAttribute("offerVisibleId", offerVisibleId);

        OfferPageOneView offerPageOne =
                offerPageOneService.getOfferPageOneViewByVisibleId(offerVisibleId);
        model.addAttribute("offerPageOne", offerPageOne);

        return "edit-offer-one";
    }

    @PutMapping("/edit/one/{offerId}")
    public String postEditOfferPageOne(@PathVariable("offerId") String offerVisibleId,
                                      @Valid OfferPageOneDTO offerPageOneDTO,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes)  {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("offerPageOneDTO", offerPageOneDTO)
                    .addFlashAttribute(BINDING_RESULT + "offerPageOneDTO", bindingResult);


            return REDIRECT_EDIT_PAGE_ONE + offerVisibleId;
        }

        OfferPageOne offerPageOne =
                offerPageOneService.getOfferPageOneByOfferVisibleId(offerVisibleId);
        offerPageOneService.editOfferPageOne(offerPageOne, offerPageOneDTO);

        return REDIRECT_EDIT_PAGE_TWO + offerVisibleId;
    }


    @GetMapping("/edit/two/{offerId}")
    public String getEditOfferPageTwo(@PathVariable("offerId") String offerVisibleId,
                                     Model model)  {


        OfferPageTwo pageTwo = offerPageTwoService.getOfferPageTwoByOfferVisibleId(offerVisibleId);

        if(pageTwo == null){
            return REDIRECT_ADD_PAGE_TWO + offerVisibleId;
        }

        OfferPageTwoView offerPageTwo = offerPageTwoService.getOfferPageTwoViewByVisibleId(pageTwo);

        List<String> allCityNames = cityService.getAllCityNames();
        model.addAttribute("cities", allCityNames);
        model.addAttribute("offerVisibleId", offerVisibleId);
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


            return REDIRECT_EDIT_PAGE_TWO + offerVisibleId;

        }

        OfferPageTwo pageTwo = offerPageTwoService.getOfferPageTwoByOfferVisibleId(offerVisibleId);

        offerPageTwoService.editPageTwo(pageTwo, offerPageTwoDTO);

        return REDIRECT_EDIT_PAGE_THREE + offerVisibleId;
    }


    @GetMapping("/edit/three/{offerId}")
    public String getEditOfferPageThree(@PathVariable("offerId") String offerVisibleId,
                                   Model model
    )  {

        model.addAttribute("offerVisibleId", offerVisibleId);

            List<PictureView> pictures =
                    offerService.getOfferPicturesByVisibleId(offerVisibleId);
            model.addAttribute("pictures", pictures);

        return "edit-offer-three";
    }

    @PostMapping("/edit/three/{offerId}")
    public String postEditOfferPageThree(@PathVariable("offerId") String offerVisibleId,
                                        @Valid OfferPageThreeDTO offerPageThreeDTO,
                                        BindingResult bindingResult,
                                        RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("offerPageThreeDTO", offerPageThreeDTO)
                    .addFlashAttribute(BINDING_RESULT + "offerPageThreeDTO", bindingResult );

            return REDIRECT_EDIT_PAGE_THREE + offerVisibleId;
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
