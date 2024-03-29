package bg.softuni.myhome.web;

import bg.softuni.myhome.model.AppUserDetails;
import bg.softuni.myhome.model.dto.*;
import bg.softuni.myhome.model.enums.OfferTypeEnum;
import bg.softuni.myhome.model.view.OfferDetailsView;
import bg.softuni.myhome.service.*;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;

import static bg.softuni.myhome.commons.StaticVariables.BINDING_RESULT;
import static bg.softuni.myhome.commons.StaticVariables.ROLE_USER;

@Controller
@RequestMapping("/offers")
public class OffersController {



    private final OfferService offerService;
    private final CategoryService categoryService;
    private final CityService cityService;
    private final SearchService searchService;
    private final RequestService requestService;
    private final AgencyService agencyService;


    public OffersController(OfferService offerService, CategoryService categoryService,
                            CityService cityService, SearchService searchService, RequestService requestService, OfferPageOneService offerPageOneService, AgencyService agencyService) {
        this.offerService = offerService;
        this.categoryService = categoryService;
        this.cityService = cityService;
        this.searchService = searchService;
        this.requestService = requestService;

        this.agencyService = agencyService;
    }

    @Secured(ROLE_USER)
    @GetMapping("/favourites")
    public String getFavouriteOffers(Model model,
                                     @AuthenticationPrincipal AppUserDetails appUserDetails) {
        if(appUserDetails == null){
            return "redirect:/";
        }
        addAttributesToModel(model);
        return "offers/favourites";
    }

    @PostMapping("/favourites")
    public String postFavSearch(@Valid SearchFormDTO searchFormDTO,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 @AuthenticationPrincipal AppUserDetails appUserDetails) {


        String visibleId = getSearchResult(bindingResult, redirectAttributes, searchFormDTO, appUserDetails,
                "redirect:favourites");

        return "redirect:/search/" + visibleId;
    }

    @GetMapping("/rent")
    public String getRent(Model model) {

        addAttributesToModel(model);
        return "offers/rent-offers";
    }


    @PostMapping("/rent")
    public String postRentSearch(@Valid SearchFormDTO searchFormDTO,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 @AuthenticationPrincipal AppUserDetails appUserDetails) {


        String visibleId = getSearchResult(bindingResult, redirectAttributes, searchFormDTO, appUserDetails,
                "redirect:rent");

        return "redirect:/search/" + visibleId;
    }


    @GetMapping("/sale")
    public String getSale(Model model) {
        addAttributesToModel(model);

        return "offers/sale-offers";
    }


    @PostMapping("/sale")
    public String postSaleSearch(@Valid SearchFormDTO searchFormDTO,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 @AuthenticationPrincipal AppUserDetails appUserDetails) {

        String visibleId = getSearchResult(bindingResult, redirectAttributes, searchFormDTO, appUserDetails,
                "redirect:sale");

        return "redirect:/search/" + visibleId;
    }

    @GetMapping("/ag/{agencyName}")
    public String getOffersByAgency(Model model, @PathVariable String agencyName) {

        addAgencyAttributesToModel(agencyName, model);
        addAttributesToModel(model);

        return "offers/offers-agency";
    }




    @PostMapping("/ag/{agencyName}")
    public String postAgencySearch(Model model, @PathVariable String agencyName,
                                   @Valid SearchFormDTO searchFormDTO,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes,
                                   @AuthenticationPrincipal AppUserDetails appUserDetails) {


        String visibleId = getSearchResult(bindingResult, redirectAttributes, searchFormDTO, appUserDetails,
                "redirect:/offers/ag/" + agencyName);

        return "redirect:/search/" + visibleId;
    }




    @GetMapping("/{visibleId}")
    public String getOfferDetails(@PathVariable String visibleId, Model model,
                                  @AuthenticationPrincipal AppUserDetails appUserDetails) {
        OfferDetailsView detailedOffer =
                offerService.findDetailedOfferByVisibleId(visibleId);

        if(appUserDetails != null){
            boolean isFavourite = offerService.isFavouriteToUser(visibleId, appUserDetails);
            if(isFavourite){
                model.addAttribute("favTrue", true);
            }
        }

        model.addAttribute("offerDetailsView", detailedOffer);
        return "offers/offer-details";
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

    @PatchMapping("/{visibleId}/fav")
    public String addToRemoveFromFavourites(@PathVariable String visibleId,
                                            RedirectAttributes redirectAttributes,
                                            @AuthenticationPrincipal AppUserDetails appUserDetails
                                           ) {

        if (appUserDetails == null) {
            redirectAttributes.addFlashAttribute("notLoggedUser", true);
            return "redirect:/offers/" + visibleId;
        }

        boolean isFavourite = offerService.isFavouriteToUser(visibleId, appUserDetails);

        if (isFavourite) {
            offerService.removeFromFavourites(visibleId, appUserDetails);
        } else {
            offerService.addToFavourites(visibleId, appUserDetails);
        }

        return "redirect:/offers/" + visibleId;
    }


    private void addAttributesToModel(Model model) {
        List<String> allCategoryNames = categoryService.getAllCategoryNames();
        List<String> allCityNames = cityService.getAllCityNames();
        model.addAttribute("offerTypeEnums", OfferTypeEnum.values());
        model.addAttribute("categories", allCategoryNames);
        model.addAttribute("cities", allCityNames);
    }


    private void addAgencyAttributesToModel(String agencyName, Model model) {
        model.addAttribute("offerTypeEnums", OfferTypeEnum.values());
        model.addAttribute("agencyName", agencyName);

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


    private String getSearchResult (BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                  SearchFormDTO searchFormDTO, AppUserDetails appUserDetails, String redirect){
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("searchFormDTO", searchFormDTO)
                    .addFlashAttribute(BINDING_RESULT + "searchFormDTO", bindingResult);

            return redirect;
        }

        return searchService.saveSearchCriteria(searchFormDTO, appUserDetails).getVisibleId();

    }



}
