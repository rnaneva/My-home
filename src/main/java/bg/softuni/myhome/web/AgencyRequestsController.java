package bg.softuni.myhome.web;


import bg.softuni.myhome.model.AppUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/agency")
public class AgencyRequestsController {


    @GetMapping("/requests/new")
    public String getNewRequests(
            @AuthenticationPrincipal AppUserDetails appUserDetails) {

        return "agency/agency-requests";

    }

    @GetMapping("/requests/replied")
    public String getRepliedRequests(
            @AuthenticationPrincipal AppUserDetails appUserDetails) {

        return "agency/agency-requests";

    }

    @GetMapping("/requests/rejected")
    public String getRejectedRequests(
            @AuthenticationPrincipal AppUserDetails appUserDetails) {

        return "agency/agency-requests";

    }

    @GetMapping("/requests/inspection")
    public String getRequestsForInspection(
            @AuthenticationPrincipal AppUserDetails appUserDetails) {

        return "agency/agency-requests";
    }


}
