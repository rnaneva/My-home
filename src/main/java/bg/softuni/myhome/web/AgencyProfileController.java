package bg.softuni.myhome.web;

import bg.softuni.myhome.model.AppUserDetails;
import bg.softuni.myhome.model.dto.AgencyCreateProfileDTO;
import bg.softuni.myhome.model.dto.AgencyEditProfileDTO;
import bg.softuni.myhome.model.entities.AgencyEntity;
import bg.softuni.myhome.model.view.AgencyView;
import bg.softuni.myhome.service.AgencyService;
import bg.softuni.myhome.service.OfferService;
import bg.softuni.myhome.service.RequestService;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

import static bg.softuni.myhome.commons.StaticVariables.*;

@Controller
@RequestMapping("/agency")
public class AgencyProfileController {


    private final AgencyService agencyService;
    private final OfferService offerService;
    private final RequestService requestService;


    public AgencyProfileController(AgencyService agencyService, OfferService offerService,
                                   RequestService requestService) {
        this.agencyService = agencyService;
        this.offerService = offerService;
        this.requestService = requestService;
    }

    @Secured(ROLE_MODERATOR)
    @GetMapping()
    public String getAgencyPage(@AuthenticationPrincipal AppUserDetails appUserDetails, Model model) {


        String userVisibleId = appUserDetails.getVisibleId();

        if (!agencyService.userHasRegisteredAgency(appUserDetails.getId())) {
            return REDIRECT_CREATE_PROFILE + appUserDetails.getVisibleId();
        }

        AgencyView agencyView = agencyService.getAgencyViewByUserId(appUserDetails.getId());
        Map<String, Integer> offers = offerService.getOffersCountForModel(userVisibleId);
        Map<String, Integer> requests = requestService.getRequestsCountForModel(userVisibleId);

        model.addAttribute("name", agencyView.getName());

        requests.forEach(model::addAttribute);
        offers.forEach(model::addAttribute);

        return "agency/agency";
    }


    @GetMapping("/profile/create")
    public String getCreateProfile(
            @AuthenticationPrincipal AppUserDetails appUserDetails,
            Model model) {

        if (agencyService.userHasRegisteredAgency(appUserDetails.getId())) {
            return REDIRECT_EDIT_PROFILE;
        }

        return "agency/create-agency-profile";

    }


    @PostMapping("/profile/create")
    public String postCreateProfile(@AuthenticationPrincipal AppUserDetails appUserDetails,
                                    @Valid AgencyCreateProfileDTO agencyCreateProfileDTO,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {


        String userVisibleId = appUserDetails.getVisibleId();
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("agencyCreateProfileDTO", agencyCreateProfileDTO)
                    .addFlashAttribute(BINDING_RESULT + "agencyCreateProfileDTO", bindingResult);


            return REDIRECT_CREATE_PROFILE;

        }

        agencyService
                .createAgencyProfile(userVisibleId, agencyCreateProfileDTO);

        return "redirect:/agency/profile/";

    }

    @GetMapping("/profile/edit")
    public String getEditAgencyProfile(
            @AuthenticationPrincipal AppUserDetails appUserDetails,
            Model model) {

        String userVisibleId = appUserDetails.getVisibleId();
        model.addAttribute("userVisibleId", userVisibleId);
        if (!agencyService.userHasRegisteredAgency(appUserDetails.getId())) {
            return REDIRECT_CREATE_PROFILE + appUserDetails.getVisibleId();
        }
        AgencyView agencyView = agencyService.getAgencyViewByUserId(appUserDetails.getId());
        model.addAttribute("agency", agencyView);

        return "agency/edit-agency-profile";
    }

    @PatchMapping("/profile/edit")
    public String patchEditProfile(
            @AuthenticationPrincipal AppUserDetails appUserDetails,
            @Valid AgencyEditProfileDTO agencyEditProfileDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("agencyEditProfileDTO", agencyEditProfileDTO)
                    .addFlashAttribute(BINDING_RESULT + "agencyEditProfileDTO", bindingResult);


            return REDIRECT_EDIT_PROFILE;

        }
        AgencyEntity agency = agencyService.findAgencyByUserId(appUserDetails.getId());
        agencyService.editAgencyProfile(agency, agencyEditProfileDTO);

        return "redirect:/agency/profile";

    }

    @GetMapping("/profile")
    public String getAgencyProfile(@AuthenticationPrincipal AppUserDetails appUserDetails,
                                   Model model) {

        AgencyView agencyView = agencyService.getAgencyViewByUserId(appUserDetails.getId());
        model.addAttribute("agencyView", agencyView);

        return "agency/agency-profile";
    }


    @ModelAttribute
    public AgencyCreateProfileDTO agencyProfileDTO() {
        return new AgencyCreateProfileDTO();
    }

    @ModelAttribute
    public AgencyEditProfileDTO agencyEditProfileDTO() {
        return new AgencyEditProfileDTO();
    }


}
