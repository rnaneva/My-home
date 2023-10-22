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



}
