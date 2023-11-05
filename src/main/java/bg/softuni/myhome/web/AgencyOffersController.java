package bg.softuni.myhome.web;

import bg.softuni.myhome.model.AppUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/agency/offers")
public class AgencyOffersController {


    @GetMapping("/active")
    public String getActiveOffers(
            @AuthenticationPrincipal AppUserDetails appUserDetails) {

        return "agency/agency-offers";

    }


    @GetMapping("/inactive")
    public String getInactiveOffers(
            @AuthenticationPrincipal AppUserDetails appUserDetails) {

        return "agency/agency-offers";

    }


}
