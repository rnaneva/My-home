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


    private final CityService cityService;
    private final AgencyService agencyService;
    private final SearchService searchService;
    private final OfferService offerService;
    private final OfferPageOneService offerPageOneService;

    public HomeController(CategoryService categoryService, CityService cityService,
                          AgencyService agencyService, SearchService searchService, OfferService offerService,
                          OfferPageOneService offerPageOneService) {

        this.cityService = cityService;
        this.agencyService = agencyService;
        this.searchService = searchService;
        this.offerService = offerService;
        this.offerPageOneService = offerPageOneService;
    }


    @GetMapping("/")
    public String home(@AuthenticationPrincipal AppUserDetails appUserDetails, Model model) {

        if (appUserDetails != null) {
            model.addAttribute("id", appUserDetails.getId());

        }

        addAttributesToHomePage(model);

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

        String visibleId =
                searchService.saveSearchCriteria(searchFormDTO, appUserDetails).getVisibleId();

        return "redirect:/search/" + visibleId;
    }

    @GetMapping("/agencies")
    public String allAgencies(Model model) {
        List<String> allAgencyNames = agencyService.getAllAgencyNames();
        model.addAttribute("agencyNames", allAgencyNames);
        return "agency/all-agencies";
    }


    @ModelAttribute
    public SearchFormDTO searchFormDTO() {
        return new SearchFormDTO();
    }

    private void addAttributesToHomePage(Model model) {

        List<String> cities = cityService.getAllCityNames();
        List<String> agencies = agencyService.getAllAgencyNames();
        model.addAttribute("cities", cities);
        model.addAttribute("agencies", agencies);

        offerPageOneService.addAttributesToModel(model);
    }



}
