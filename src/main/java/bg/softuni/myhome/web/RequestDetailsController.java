package bg.softuni.myhome.web;

import bg.softuni.myhome.model.dto.AgencyRequestDTO;
import bg.softuni.myhome.model.enums.RequestStatusEnum;
import bg.softuni.myhome.model.view.RequestView;
import bg.softuni.myhome.service.RequestService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/agency/requests")
public class RequestDetailsController {

    private final RequestService requestService;

    public RequestDetailsController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/{requestId}")
    public String getRequestDetails(@PathVariable Long requestId,
                                    Model model) {

        RequestView request = requestService.getRequestViewById(requestId);
        model.addAttribute("request", request);
        model.addAttribute("requestStatusEnums", RequestStatusEnum.values());


        return "agency/request-details";
    }


    @PostMapping("/{requestId}")
    public String postRequestDetails(@PathVariable Long requestId,
                                     @Valid AgencyRequestDTO agencyRequestDTO)  {

        requestService.editRequest(requestId, agencyRequestDTO);

        return "redirect:/agency/requests/" + requestId;
    }



    @ModelAttribute
    public AgencyRequestDTO agencyRequestDTO(){
        return new AgencyRequestDTO();
    }

}
