package bg.softuni.myhome.web;

import bg.softuni.myhome.model.AppUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.naming.NoPermissionException;

@Controller
@RequestMapping("/agency/requests/")
public class AgencyRequestsController {


    @GetMapping("/new/{id}")
    public String getNewRequests(@PathVariable("id") String userVisibleId,
                                 @AuthenticationPrincipal AppUserDetails appUserDetails) throws NoPermissionException {

        authorize(userVisibleId, appUserDetails);

        return "agency-requests";

    }

    @GetMapping("/replied/{id}")
    public String getRepliedRequests(@PathVariable("id") String userVisibleId,
                                     @AuthenticationPrincipal AppUserDetails appUserDetails) throws NoPermissionException {

        authorize(userVisibleId, appUserDetails);

        return "agency-requests";

    }

    @GetMapping("/rejected/{id}")
    public String getRejectedRequests(@PathVariable("id") String userVisibleId,
                                      @AuthenticationPrincipal AppUserDetails appUserDetails) throws NoPermissionException {

        authorize(userVisibleId, appUserDetails);

        return "agency-requests";

    }

    @GetMapping("/inspection/{id}")
    public String getRequestsForInspection(@PathVariable("id") String userVisibleId,
                                           @AuthenticationPrincipal AppUserDetails appUserDetails) throws NoPermissionException {

        authorize(userVisibleId, appUserDetails);

        return "agency-requests";

    }



    private static void authorize(String userVisibleId, AppUserDetails appUserDetails) throws NoPermissionException {
        if (!appUserDetails.getVisibleId().equals(userVisibleId)) {
            throw new NoPermissionException();
        }
    }

}