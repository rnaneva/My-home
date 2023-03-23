package bg.softuni.myhome.web;

import bg.softuni.myhome.model.AppUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal AppUserDetails appUserDetails, Model model){

        if(appUserDetails != null){
            model.addAttribute("names", appUserDetails.getNames())
                    .addAttribute("email", appUserDetails.getEmail());
        }

        return "index";
    }

}
