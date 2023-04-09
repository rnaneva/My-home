package bg.softuni.myhome.web;

import bg.softuni.myhome.model.AppUserDetails;
import bg.softuni.myhome.model.dto.SearchFormDTO;
import bg.softuni.myhome.model.view.OfferView;
import bg.softuni.myhome.service.*;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static bg.softuni.myhome.commons.StaticVariables.BINDING_RESULT;

@Controller
public class HomeController {


    private final CategoryService categoryService;
    private final CityService cityService;
    private final AgencyService agencyService;
    private final SearchService searchService;
    private final OfferService offerService;

    public HomeController(CategoryService categoryService, CityService cityService, AgencyService agencyService, SearchService searchService, OfferService offerService) {

        this.categoryService = categoryService;
        this.cityService = cityService;
        this.agencyService = agencyService;
        this.searchService = searchService;
        this.offerService = offerService;
    }


    @GetMapping("/")
    public String home(@AuthenticationPrincipal AppUserDetails appUserDetails, Model model) {

        if (appUserDetails != null) {
            model.addAttribute("id", appUserDetails.getId());

        }


        List<String> allCityNames = cityService.getAllCityNames();
        List<String> allCategoryNames = categoryService.getAllCategoryNames();
        List<String> allAgencyNames = agencyService.getAllAgencyNames();
        model.addAttribute("categories", allCategoryNames);
        model.addAttribute("cities", allCityNames);
        model.addAttribute("agencies", allAgencyNames);

        List<OfferView> last4AddedOffers = offerService.findLastFourAddedOffers();
        if (last4AddedOffers.isEmpty()) {
            model.addAttribute("no_last_offers", true);
        }
        model.addAttribute("lastAddedOffers", last4AddedOffers);

        return "index";
    }

    @PostMapping("/")
    public String postAdvancedSearch(@Valid SearchFormDTO searchFormDTO,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes,
                                     @AuthenticationPrincipal AppUserDetails appUserDetails) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("searchFormDTO", searchFormDTO)
                    .addFlashAttribute(BINDING_RESULT + "searchFormDTO", bindingResult);

            return "redirect:/#advanced-search-title";
        }

        String visibleId = searchService.saveSearchCriteria(searchFormDTO, appUserDetails);

        return "redirect:/search/" + visibleId;
    }


    @ModelAttribute
    public SearchFormDTO searchFormDTO() {
        return new SearchFormDTO();
    }

}
