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
@RequestMapping("/agency/requests")
public class RequestDetailsController {

    private final RequestService requestService;

    public RequestDetailsController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/{requestId}")
    public String getRequestDetails(@PathVariable Long requestId,
                                    @AuthenticationPrincipal AppUserDetails appUserDetails,
                                    Model model) {


        RequestView request = requestService.getRequestViewById(requestId);
        model.addAttribute("request", request);


        return "request-details";
    }


    @PostMapping("/{requestId}")
    public String postRequestDetails(@PathVariable Long requestId,
                                     @AuthenticationPrincipal AppUserDetails appUserDetails,
                                     @Valid @ModelAttribute("agencyRequestDTO") AgencyRequestDTO agencyRequestDTO,
                                     Model model) throws NoPermissionException {


        requestService.editRequest(requestId, agencyRequestDTO);

        return "redirect:/agency/requests/" + requestId;
    }



    @ModelAttribute
    public AgencyRequestDTO agencyRequestDTO(){
        return new AgencyRequestDTO();
    }

}
