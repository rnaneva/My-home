package bg.softuni.myhome.web;

import bg.softuni.myhome.model.AppUserDetails;
import bg.softuni.myhome.model.dto.*;
import bg.softuni.myhome.model.view.OfferDetailsView;
import bg.softuni.myhome.model.view.OfferView;
import bg.softuni.myhome.service.*;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static bg.softuni.myhome.commons.StaticVariables.BINDING_RESULT;

@Controller
@RequestMapping("/offers")
public class OffersController {

//todo all rest

    private final OfferService offerService;
    private final CategoryService categoryService;
    private final CityService cityService;
    private final SearchService searchService;
    private final RequestService requestService;
    private final AgencyService agencyService;

    public OffersController(OfferService offerService, CategoryService categoryService,
                            CityService cityService, SearchService searchService, RequestService requestService, AgencyService agencyService) {
        this.offerService = offerService;
        this.categoryService = categoryService;
        this.cityService = cityService;
        this.searchService = searchService;
        this.requestService = requestService;
        this.agencyService = agencyService;
    }


    @GetMapping("/rent")
    public String getRent(Model model) {
        List<OfferView> offers = offerService.allRentProperties();
        model.addAttribute("rentOffers", offers);
        addCategoriesAndCitiesToModel(model);

        return "rent-offers";
    }


    @PostMapping("/rent")
    public String postRentSearch(@Valid SearchFormDTO searchFormDTO,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 @AuthenticationPrincipal AppUserDetails appUserDetails) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("searchFormDTO", searchFormDTO)
                    .addFlashAttribute(BINDING_RESULT + "searchFormDTO", bindingResult);

            return "redirect:rent";
        }

        String visibleId =
                searchService.saveSearchCriteria(searchFormDTO, appUserDetails).getVisibleId();

        return "redirect:/search/" + visibleId;
    }


    @GetMapping("/sale")
    public String getSale(Model model) {
        List<OfferView> offers = offerService.allSaleProperties();
        model.addAttribute("saleOffers", offers);
        addCategoriesAndCitiesToModel(model);

        return "sale-offers";
    }


    @PostMapping("/sale")
    public String postSaleSearch(@Valid SearchFormDTO searchFormDTO,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 @AuthenticationPrincipal AppUserDetails appUserDetails) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("searchFormDTO", searchFormDTO)
                    .addFlashAttribute(BINDING_RESULT + "searchFormDTO", bindingResult);

            return "redirect:sale";
        }

        String visibleId =
                searchService.saveSearchCriteria(searchFormDTO, appUserDetails).getVisibleId();

        return "redirect:/search/" + visibleId;
    }


    @GetMapping("/{visibleId}")
    public String getOfferDetails(@PathVariable String visibleId, Model model) {
        OfferDetailsView detailedOffer =
                offerService.findDetailedOfferByVisibleId(visibleId);
//        todo changed : model.addAttribute(detailedOffer);
        model.addAttribute("offerDetailsView", detailedOffer);

        return "offer-details";
    }


    @PostMapping("/{visibleId}")
    public String postRequest(@PathVariable String visibleId, @Valid UserRequestDTO userRequestDTO,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRequestDTO", userRequestDTO)
                    .addFlashAttribute(BINDING_RESULT + "userRequestDTO", bindingResult);

            return "redirect:/offers/" + visibleId;
        }

        requestService.saveRequest(userRequestDTO, visibleId);


        return "successful-message";
    }


    @GetMapping("/ag/{agencyId}")
    public String getOffersByAgency(Model model, @PathVariable Long agencyId) {
        List<OfferView> offers = offerService.getOffersByAgencyId(agencyId);
        model.addAttribute("agencyOffers", offers);
        addAgencyNameAndIdToModel(agencyId, model);
        addCategoriesAndCitiesToModel(model);


        return "offers-agency";
    }




    @PostMapping("/ag/{agencyId}")
    public String postAgencySearch(Model model, @Valid SearchFormDTO searchFormDTO,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes,
                                   @AuthenticationPrincipal AppUserDetails appUserDetails,
                                   @PathVariable Long agencyId) {

        addAgencyNameAndIdToModel(agencyId, model);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("searchFormDTO", searchFormDTO)
                    .addFlashAttribute(BINDING_RESULT + "searchFormDTO", bindingResult);

            return "redirect:/offers/ag/" + agencyId;
        }

        String visibleId =
                searchService.saveSearchCriteria(searchFormDTO, appUserDetails).getVisibleId();

        return "redirect:/search/" + visibleId;
    }

    private void addCategoriesAndCitiesToModel(Model model) {
        List<String> allCategoryNames = categoryService.getAllCategoryNames();
        List<String> allCityNames = cityService.getAllCityNames();
        model.addAttribute("categories", allCategoryNames);
        model.addAttribute("cities", allCityNames);
    }
    private void addAgencyNameAndIdToModel(Long agencyId, Model model) {
        String agencyName = agencyService.findAgencyNameById(agencyId);
        model.addAttribute("agencyName", agencyName);
        model.addAttribute("id", agencyId);
    }

    @ModelAttribute
    public SearchFormDTO searchFormDTO() {
        return new SearchFormDTO();
    }

    @ModelAttribute
    public OfferDetailsView offerDetailsView() {
        return new OfferDetailsView();
    }

    @ModelAttribute
    public UserRequestDTO userRequestDTO() {
        return new UserRequestDTO();
    }

}
