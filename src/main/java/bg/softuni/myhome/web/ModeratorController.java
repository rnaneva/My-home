package bg.softuni.myhome.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ModeratorController {

    @GetMapping("/agency")
    public String getModerator(){
        return "agency";
    }
}


