package bg.softuni.myhome.web;

import bg.softuni.myhome.model.AppUserDetails;
import bg.softuni.myhome.model.dto.AgencyRequestDTO;
import bg.softuni.myhome.model.view.RequestView;
import bg.softuni.myhome.service.RequestService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import javax.naming.NoPermissionException;


@Controller
public class RequestController {

    private RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/agency/{userVisibleId}/requests/{requestId}")
    public String getRequestDetails(@PathVariable("userVisibleId") String userVisibleId,
                                    @PathVariable("requestId") Long requestId,
                                    @AuthenticationPrincipal AppUserDetails appUserDetails,
                                    Model model) throws NoPermissionException {

//        authorize(userVisibleId, appUserDetails); todo not working here

        RequestView request = requestService.getRequestViewById(requestId);
        model.addAttribute("request", request);

        return "request-details";
    }


    @PostMapping("/agency/{userVisibleId}/requests/{requestId}")
    public String postRequestDetails(@PathVariable("userVisibleId") String userVisibleId,
                                     @PathVariable("requestId") Long requestId,
                                     @AuthenticationPrincipal AppUserDetails appUserDetails,
                                     @Valid @ModelAttribute("agencyRequestDTO")AgencyRequestDTO agencyRequestDTO,
                                    Model model) throws NoPermissionException {

//        authorize(userVisibleId, appUserDetails); todo not working here
        model.addAttribute("userVisibleId", userVisibleId);

       requestService.editRequest(requestId, agencyRequestDTO);

        return "redirect:/agency/{userVisibleId}/requests/{requestId}";
    }

    @ModelAttribute
    public AgencyRequestDTO agencyRequestDTO(){
        return new AgencyRequestDTO();
    }

    private static void authorize(String userVisibleId, AppUserDetails appUserDetails) throws NoPermissionException {
        if (!appUserDetails.getVisibleId().equals(userVisibleId)) {
            throw new NoPermissionException();
        }
    }
}
