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


    public OffersController(OfferService offerService, CategoryService categoryService,
                            CityService cityService, SearchService searchService, RequestService requestService) {
        this.offerService = offerService;
        this.categoryService = categoryService;
        this.cityService = cityService;
        this.searchService = searchService;
        this.requestService = requestService;

    }

    @GetMapping("/favourites")
    public String getFavouriteOffers(Model model,
                                     @AuthenticationPrincipal AppUserDetails appUserDetails) {
        if(appUserDetails == null){
            return "redirect:/";
        }
        List<OfferView> favourites = offerService.getFavouriteOffersForUser(appUserDetails);
        model.addAttribute("favourites", favourites);
        addCategoriesAndCitiesToModel(model);
        return "favourites";
    }

    @PostMapping("/favourites")
    public String postFavSearch(@Valid SearchFormDTO searchFormDTO,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 @AuthenticationPrincipal AppUserDetails appUserDetails) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("searchFormDTO", searchFormDTO)
                    .addFlashAttribute(BINDING_RESULT + "searchFormDTO", bindingResult);

            return "redirect:favourites";
        }

        String visibleId =
                searchService.saveSearchCriteria(searchFormDTO, appUserDetails).getVisibleId();

        return "redirect:/search/" + visibleId;
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

    @PatchMapping("/{visibleId}")
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


    void addCategoriesAndCitiesToModel(Model model) {
        List<String> allCategoryNames = categoryService.getAllCategoryNames();
        List<String> allCityNames = cityService.getAllCityNames();
        model.addAttribute("categories", allCategoryNames);
        model.addAttribute("cities", allCityNames);
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
