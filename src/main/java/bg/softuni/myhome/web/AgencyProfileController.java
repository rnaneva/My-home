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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Map;

import static bg.softuni.myhome.commons.StaticVariables.*;

@Controller
@RequestMapping("/agency")
public class AgencyProfileController {



    private final AgencyService agencyService;
    private final OfferService offerService;
    private final RequestService requestService;

    @Autowired
    public AgencyProfileController(AgencyService agencyService, OfferService offerService, RequestService requestService) {
        this.agencyService = agencyService;
        this.offerService = offerService;
        this.requestService = requestService;
    }


    @GetMapping("/{id}")
    public String getAgencyPage(@PathVariable("id") String userVisibleId,
                                @AuthenticationPrincipal AppUserDetails appUserDetails,Model model) {


        if (!agencyService.userHasRegisteredAgency(appUserDetails.getId())) {
            return REDIRECT_CREATE_PROFILE + appUserDetails.getVisibleId();
        }

        AgencyView agencyView = agencyService.getAgencyViewByUserId(appUserDetails.getId());
        Map<String, Integer> offers = offerService.getOffersCountForModel(userVisibleId);
        Map<String, Integer> requests = requestService.getRequestsCountForModel(userVisibleId);

        model.addAttribute("name", agencyView.getName());

        requests.forEach(model::addAttribute);
        offers.forEach(model::addAttribute);

        return "agency";
    }


    @GetMapping("/profile/create/{userVisibleId}")
    public String getCreateProfile(@AuthenticationPrincipal AppUserDetails appUserDetails,
                                   @PathVariable String userVisibleId)  {


        if (agencyService.userHasRegisteredAgency(appUserDetails.getId())) {
            return REDIRECT_EDIT_PROFILE + appUserDetails.getVisibleId();
        }

        return "create-agency-profile";

    }


    @PostMapping("/profile/create/{userVisibleId}")
    public String postCreateProfile(@Valid AgencyCreateProfileDTO agencyCreateProfileDTO,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes,
                                    @PathVariable String userVisibleId) throws IOException {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("agencyCreateProfileDTO", agencyCreateProfileDTO)
                    .addFlashAttribute(BINDING_RESULT + "agencyCreateProfileDTO", bindingResult);


            return REDIRECT_CREATE_PROFILE + userVisibleId;

        }

        agencyService
                .createAgencyProfile(userVisibleId, agencyCreateProfileDTO);

        return "redirect:/agency/profile/" + userVisibleId;

    }

    @GetMapping("/profile/edit/{id}")
    public String getEditAgencyProfile(@PathVariable("id") String userVisibleId, Model model,
                                       @AuthenticationPrincipal AppUserDetails appUserDetails){




        model.addAttribute("userVisibleId", userVisibleId);
        if (!agencyService.userHasRegisteredAgency(appUserDetails.getId())) {
            return REDIRECT_CREATE_PROFILE + appUserDetails.getVisibleId();
        }
        AgencyView agencyView = agencyService.getAgencyViewByUserId(appUserDetails.getId());
        model.addAttribute("agency", agencyView);

        return "edit-agency-profile";
    }

    @PatchMapping("/profile/edit/{id}")
    public String patchEditProfile(@PathVariable("id") String userVisibleId,
                                   @AuthenticationPrincipal AppUserDetails appUserDetails,
                                   @Valid AgencyEditProfileDTO agencyEditProfileDTO,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) throws IOException {


        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("agencyEditProfileDTO", agencyEditProfileDTO)
                    .addFlashAttribute(BINDING_RESULT + "agencyEditProfileDTO", bindingResult);


            return REDIRECT_EDIT_PROFILE + userVisibleId;

        }
        AgencyEntity agency = agencyService.findAgencyByUserId(appUserDetails.getId());
        agencyService.editAgencyProfile(agency, agencyEditProfileDTO);

        return "redirect:/agency/profile/" + userVisibleId;

    }

    @GetMapping("/profile/{id}")
    public String getAgencyProfile(@PathVariable("id") String userVisibleId, Model model,
                                   @AuthenticationPrincipal AppUserDetails appUserDetails) {


        AgencyView agencyView = agencyService.getAgencyViewByUserId(appUserDetails.getId());
        model.addAttribute("agencyView", agencyView);

        return "agency-profile";
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
