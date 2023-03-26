package bg.softuni.myhome.web;

import bg.softuni.myhome.model.AppUserDetails;
import bg.softuni.myhome.model.dto.OfferDTO;
import bg.softuni.myhome.model.dto.SearchDTO;
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

import static bg.softuni.myhome.staticVariables.StaticVariables.BINDING_RESULT;

@Controller
public class HomeController {


    private CategoryService categoryService;
    private CityService cityService;
    private AgencyService agencyService;
    private SearchService searchService;
    private OfferService offerService;

    public HomeController(CategoryService categoryService, CityService cityService, AgencyService agencyService, SearchService searchService, OfferService offerService) {

        this.categoryService = categoryService;
        this.cityService = cityService;
        this.agencyService = agencyService;
        this.searchService = searchService;
        this.offerService = offerService;
    }

    //    todo logged user
    @GetMapping("/")
    public String home(@AuthenticationPrincipal AppUserDetails appUserDetails, Model model){

        if(appUserDetails != null){
            model.addAttribute("names", appUserDetails.getNames())
                    .addAttribute("email", appUserDetails.getEmail());
        }

        List<String> allCityNames = cityService.getAllCityNames();
        List<String> allCategoryNames = categoryService.getAllCategoryNames();
        List<String> allAgenciesNames = agencyService.getAllAgenciesNames();
        model.addAttribute("categories", allCategoryNames);
        model.addAttribute("cities", allCityNames);
        model.addAttribute("agencies", allAgenciesNames);

        List<OfferDTO> last4AddedOffers = offerService.findLast4AddedOffers();
        if(last4AddedOffers.isEmpty()){
            model.addAttribute("no_last_offers", true);
        }
        model.addAttribute("lastAddedOffers", last4AddedOffers);

        return "index";
    }

    @PostMapping("/")
    public String makeAdvancedSearch(@Valid SearchDTO searchDTO,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("searchDTO", searchDTO)
                    .addFlashAttribute(BINDING_RESULT + "searchDTO", bindingResult);

            return "redirect:/";
        }

        String visibleId = searchService.saveSearchCriteria(searchDTO);

        // TODO: 26/03/2023
        return "redirect:/search/" + visibleId;
    }


    @ModelAttribute
    public SearchDTO searchDTO(){
        return new SearchDTO();
    }

}
