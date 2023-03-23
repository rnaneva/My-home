package bg.softuni.myhome.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class AdminController {


    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }
}
