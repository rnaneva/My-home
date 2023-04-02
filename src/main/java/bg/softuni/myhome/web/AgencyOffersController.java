package bg.softuni.myhome.web;

import bg.softuni.myhome.model.AppUserDetails;
import bg.softuni.myhome.model.dto.AgencyProfileDTO;
import bg.softuni.myhome.model.dto.OfferAddPageOneDTO;
import bg.softuni.myhome.model.entities.AgencyEntity;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.NoPermissionException;

import static bg.softuni.myhome.commons.StaticVariables.BINDING_RESULT;

@Controller
@RequestMapping("/agency/offers")
public class AgencyOffersController {


    @GetMapping("/active/{id}")
    public String getActiveOffers(@PathVariable("id") String userVisibleId,
                                  @AuthenticationPrincipal AppUserDetails appUserDetails) throws NoPermissionException {

        authorize(userVisibleId, appUserDetails);

        return "agency-offers";

    }


    @GetMapping("/inactive/{id}")
    public String getInactiveOffers(@PathVariable("id") String userVisibleId,
                                    @AuthenticationPrincipal AppUserDetails appUserDetails) throws NoPermissionException {

        authorize(userVisibleId, appUserDetails);

        return "agency-offers";

    }



    private static void authorize(String userVisibleId, AppUserDetails appUserDetails) throws NoPermissionException {
        if (!appUserDetails.getVisibleId().equals(userVisibleId)) {
            throw new NoPermissionException();
        }
    }


}
