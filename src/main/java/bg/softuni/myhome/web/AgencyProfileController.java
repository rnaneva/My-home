package bg.softuni.myhome.web;

import bg.softuni.myhome.model.AppUserDetails;
import bg.softuni.myhome.model.dto.AgencyProfileDTO;
import bg.softuni.myhome.model.entities.AgencyEntity;
import bg.softuni.myhome.model.view.AgencyView;
import bg.softuni.myhome.service.AgencyService;
import bg.softuni.myhome.service.OfferService;
import bg.softuni.myhome.service.RequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.NoPermissionException;
import java.io.IOException;
import java.util.Map;

import static bg.softuni.myhome.commons.StaticVariables.BINDING_RESULT;

@Controller
@RequestMapping("/agency")
public class AgencyProfileController {

    private final static String REDIRECT_EDIT = "redirect:/agency/edit/";
    private final static String REDIRECT_CREATE = "redirect:/agency/profile/create";

    private final AgencyService agencyService;
    private final OfferService offerService;
    private final RequestService requestService;

    @Autowired
    public AgencyProfileController(AgencyService agencyService, OfferService offerService, RequestService requestService) {
        this.agencyService = agencyService;
        this.offerService = offerService;
        this.requestService = requestService;
    }

    @GetMapping("/profile/create")
    public String getCreateProfile(@AuthenticationPrincipal AppUserDetails appUserDetails) {

        if (agencyService.userHasRegisteredAgency(appUserDetails.getId())) {
            return REDIRECT_EDIT + appUserDetails.getVisibleId();
        }
        return "create-agency-profile";

    }


    @PostMapping("/profile/create")
    public String postCreateProfile(@Valid @ModelAttribute("agencyProfileDTO") AgencyProfileDTO agencyProfileDTO,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes,
                                    @AuthenticationPrincipal AppUserDetails appUserDetails) throws IOException {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("agencyProfileDTO", agencyProfileDTO)
                    .addFlashAttribute(BINDING_RESULT + "agencyProfileDTO", bindingResult);


            return REDIRECT_CREATE;

        }

        AgencyEntity agency = agencyService
                .createAgencyProfile(appUserDetails, agencyProfileDTO);
        String visibleId = agency.getUser().getVisibleId();

        return "redirect:/agency/profile/" + visibleId;

    }

    @GetMapping("/edit/{id}")
    public String getEditAgencyProfile(@PathVariable("id") String userVisibleId, Model model,
                                       @AuthenticationPrincipal AppUserDetails appUserDetails) throws NoPermissionException {

//        todo handle
        authorize(userVisibleId, appUserDetails);

        if (!agencyService.userHasRegisteredAgency(appUserDetails.getId())) {
            return REDIRECT_CREATE;
        }
        AgencyView agencyView = agencyService.getAgencyViewByUserId(appUserDetails.getId());
        model.addAttribute("agency", agencyView);

        return "edit-agency-profile";
    }

    @PatchMapping("/edit/{id}")
    public String postEditProfile(@Valid @ModelAttribute("agencyProfileDTO") AgencyProfileDTO agencyProfileDTO,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes,
                                  @AuthenticationPrincipal AppUserDetails appUserDetails,
                                  @PathVariable("id") String userVisibleId) throws IOException, NoPermissionException {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("agencyProfileDTO", agencyProfileDTO)
                    .addFlashAttribute(BINDING_RESULT + "agencyProfileDTO", bindingResult);


            return "redirect:/agency/edit/{id}";

        }

        AgencyEntity agency = agencyService.findAgencyByUserId(appUserDetails.getId());
        agencyService.editAgencyProfile(agency, agencyProfileDTO);

        return "redirect:/agency/profile/" + userVisibleId;

    }

    @GetMapping("/profile/{id}")
    public String getAgencyProfile(@PathVariable("id") String userVisibleId, Model model,
                                   @AuthenticationPrincipal AppUserDetails appUserDetails) throws NoPermissionException {


        authorize(userVisibleId, appUserDetails);
        AgencyView agencyView = agencyService.getAgencyViewByUserId(appUserDetails.getId());
        model.addAttribute("agencyView", agencyView);
        return "agency-profile";
    }


    @GetMapping("/{id}")
    public String getAgencyPage(@PathVariable("id") String userVisibleId,
                                Model model,
                                @AuthenticationPrincipal AppUserDetails appUserDetails) throws NoPermissionException {


        authorize(userVisibleId, appUserDetails);

        if (!agencyService.userHasRegisteredAgency(appUserDetails.getId())) {
            return REDIRECT_CREATE;
        }

        AgencyView agencyView = agencyService.getAgencyViewByUserId(appUserDetails.getId());
        Map<String, Integer> offers = offerService.getOffersCountForModel(userVisibleId);
        Map<String, Integer> requests = requestService.getRequestsCountForModel(userVisibleId);

        model.addAttribute("name", agencyView.getName());
        requests.forEach(model::addAttribute);
        offers.forEach(model::addAttribute);

        return "agency";
    }





    @ModelAttribute
    public AgencyProfileDTO agencyProfileDTO() {
        return new AgencyProfileDTO();
    }

    //  todo
    private static void authorize(String userVisibleId, AppUserDetails appUserDetails) throws NoPermissionException {
        if (!appUserDetails.getVisibleId().equals(userVisibleId)) {
            throw new NoPermissionException();
        }
    }

}
