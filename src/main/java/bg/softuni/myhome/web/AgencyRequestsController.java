package bg.softuni.myhome.web;


import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/agency")
public class AgencyRequestsController {


    @GetMapping("/requests/new/{id}")
    public String getNewRequests(@PathVariable("id") String userVisibleId)  {


        return "agency-requests";

    }

    @GetMapping("/requests/replied/{id}")
    public String getRepliedRequests(@PathVariable("id") String userVisibleId) {


        return "agency-requests";

    }

    @GetMapping("/requests/rejected/{id}")
    public String getRejectedRequests(@PathVariable("id") String userVisibleId)  {


        return "agency-requests";

    }

    @GetMapping("/requests/inspection/{id}")
    public String getRequestsForInspection(@PathVariable("id") String userVisibleId) {

        return "agency-requests";

    }



}
