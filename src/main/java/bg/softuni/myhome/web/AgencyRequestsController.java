package bg.softuni.myhome.web;

import bg.softuni.myhome.model.AppUserDetails;
import bg.softuni.myhome.model.dto.AgencyRequestDTO;
import bg.softuni.myhome.model.view.RequestView;
import bg.softuni.myhome.service.RequestService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.naming.NoPermissionException;

@Controller
@RequestMapping("/agency")
public class AgencyRequestsController {


    @GetMapping("/requests/new/{id}")
    public String getNewRequests(@PathVariable("id") String userVisibleId,
                                 @AuthenticationPrincipal AppUserDetails appUserDetails) throws NoPermissionException {


        return "agency-requests";

    }

    @GetMapping("/requests/replied/{id}")
    public String getRepliedRequests(@PathVariable("id") String userVisibleId,
                                     @AuthenticationPrincipal AppUserDetails appUserDetails) throws NoPermissionException {


        return "agency-requests";

    }

    @GetMapping("/requests/rejected/{id}")
    public String getRejectedRequests(@PathVariable("id") String userVisibleId,
                                      @AuthenticationPrincipal AppUserDetails appUserDetails) throws NoPermissionException {


        return "agency-requests";

    }

    @GetMapping("/requests/inspection/{id}")
    public String getRequestsForInspection(@PathVariable("id") String userVisibleId,
                                           @AuthenticationPrincipal AppUserDetails appUserDetails) throws NoPermissionException {

        return "agency-requests";

    }



}
