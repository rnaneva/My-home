package bg.softuni.myhome.web;

import bg.softuni.myhome.model.AppUserDetails;
import bg.softuni.myhome.model.dto.SearchFormDTO;
import bg.softuni.myhome.model.view.OfferView;
import bg.softuni.myhome.service.AgencyService;
import bg.softuni.myhome.service.OfferService;
import bg.softuni.myhome.service.SearchService;
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
public class AllAgenciesController {

    private final OfferService offerService;
    private final AgencyService agencyService;
    private final SearchService searchService;
    private OffersController offersController;

    public AllAgenciesController(OfferService offerService, AgencyService agencyService, SearchService searchService, OffersController offersController) {
        this.offerService = offerService;
        this.agencyService = agencyService;
        this.searchService = searchService;
        this.offersController = offersController;
    }

    @GetMapping("/offers/ag/{agencyId}")
    public String getOffersByAgency(Model model, @PathVariable Long agencyId) {
        List<OfferView> offers = offerService.getOffersByAgencyId(agencyId);
        model.addAttribute("agencyOffers", offers);
        addAgencyNameAndIdToModel(agencyId, model);
        offersController.addCategoriesAndCitiesToModel(model);


        return "offers-agency";
    }




    @PostMapping("/offers/ag/{agencyId}")
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



    private void addAgencyNameAndIdToModel(Long agencyId, Model model) {
        String agencyName = agencyService.findAgencyNameById(agencyId);
        model.addAttribute("agencyName", agencyName);
        model.addAttribute("id", agencyId);
    }

    @ModelAttribute
    public SearchFormDTO searchFormDTO() {
        return new SearchFormDTO();
    }

}
