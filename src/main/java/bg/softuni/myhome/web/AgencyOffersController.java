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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static bg.softuni.myhome.commons.StaticVariables.BINDING_RESULT;


@Controller
@RequestMapping("/agency/offers")
public class AgencyOffersController {

    private final AgencyService agencyService;
    private final OfferService offerService;
    private final CategoryService categoryService;
    private final CityService cityService;
    private final SearchService searchService;

    public AgencyOffersController(AgencyService agencyService, OfferService offerService,
                                  CategoryService categoryService, CityService cityService, SearchService searchService) {
        this.agencyService = agencyService;
        this.offerService = offerService;
        this.categoryService = categoryService;
        this.cityService = cityService;
        this.searchService = searchService;
    }

    @GetMapping("/active/{id}")
    public String getActiveOffers(@PathVariable("id") String userVisibleId,
                                  @AuthenticationPrincipal AppUserDetails appUserDetails){

        return "agency-offers";

    }


    @GetMapping("/inactive/{id}")
    public String getInactiveOffers(@PathVariable("id") String userVisibleId,
                                    @AuthenticationPrincipal AppUserDetails appUserDetails){

        return "agency-offers";

    }


    @GetMapping("/{id}")
    public String getByAgency(Model model, @PathVariable() long id) {
        List<OfferView> agencyOffers = offerService.getOffersByAgencyId(id);
        List<String> allCategoryNames = categoryService.getAllCategoryNames();
        List<String> allCityNames = cityService.getAllCityNames();
        String name = agencyService.getAgencyNameById(id);
        model.addAttribute("name", name);
        model.addAttribute("agencyOffers", agencyOffers);
        model.addAttribute("categories", allCategoryNames);
        model.addAttribute("cities", allCityNames);

        return "offers-agency";
    }


    @PostMapping("/{id}")
    public String postSearchAgencyPage(@PathVariable() long id, @Valid SearchFormDTO searchFormDTO,
                                       BindingResult bindingResult,
                                       RedirectAttributes redirectAttributes,
                                       @AuthenticationPrincipal AppUserDetails appUserDetails) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("searchFormDTO", searchFormDTO)
                    .addFlashAttribute(BINDING_RESULT + "searchFormDTO", bindingResult);

            return "redirect:/agencies/" + id;
        }

        String visibleId =
                searchService.saveSearchCriteria(searchFormDTO,appUserDetails).getVisibleId();

        return "redirect:/search/" + visibleId;
    }



}
