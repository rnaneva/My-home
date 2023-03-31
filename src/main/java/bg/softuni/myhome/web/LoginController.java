package bg.softuni.myhome.web;

import bg.softuni.myhome.model.dto.UserLoginDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
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
    private final SecurityContextRepository securityContextRepository;

    public LoginController(UserDetailsService userDetailsService, SecurityContextRepository securityContextRepository) {

        this.userDetailsService = userDetailsService;
        this.securityContextRepository = securityContextRepository;
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

//    todo forgotten password


}
