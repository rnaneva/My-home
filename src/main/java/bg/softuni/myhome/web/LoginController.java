package bg.softuni.myhome.web;

import bg.softuni.myhome.model.AppUserDetails;
import bg.softuni.myhome.model.dto.UserLoginDTO;
import bg.softuni.myhome.model.entities.UserEntity;
import bg.softuni.myhome.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.function.Consumer;

@Controller
@RequestMapping("/users")
public class LoginController {

    private final static String USERNAME = UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY;


    private final UserDetailsService userDetailsService;
    private final UserService userService;


    public LoginController(UserDetailsService userDetailsService, UserService userService) {

        this.userDetailsService = userDetailsService;

        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(UserLoginDTO userLoginDTO,
                            Consumer<Authentication> authenticationConsumer) {

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(userLoginDTO.getUsername());


        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        authenticationConsumer.accept(authentication);

        return "redirect:/";

    }

    @PostMapping("/login-error")
    public String failLogin(@ModelAttribute(USERNAME) String username,
                            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(USERNAME, username)
                .addFlashAttribute("bad_credentials", true);

        return "redirect:login";
    }

    @GetMapping("/login/forgotten-password")
    public String forgottenPassword(Model model,
                                    @AuthenticationPrincipal AppUserDetails appUserDetails){
        UserEntity user = userService.findById(appUserDetails.getId());
        model.addAttribute("user", user);
        return "forgotten-password";
    }





}
