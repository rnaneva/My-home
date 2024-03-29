package bg.softuni.myhome.web;

import bg.softuni.myhome.model.dto.CodeDTO;
import bg.softuni.myhome.model.dto.EmailDTO;
import bg.softuni.myhome.model.dto.NewPasswordDTO;
import bg.softuni.myhome.model.entities.PasswordChangeCode;
import bg.softuni.myhome.service.UserCodeGenerationService;
import bg.softuni.myhome.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

import static bg.softuni.myhome.commons.StaticVariables.BINDING_RESULT;

@Controller
@RequestMapping("/users")
public class LoginController {

    private final static String USERNAME = UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY;

    private final UserService userService;
    private final UserCodeGenerationService userCodeGenerationService;


    public LoginController(UserService userService, UserCodeGenerationService userCodeGenerationService) {
        this.userService = userService;
        this.userCodeGenerationService = userCodeGenerationService;
    }

    @GetMapping("/login")
    public String getLogin() {
        return "auth/login";
    }



    @PostMapping("/login-error")
    public String failLogin(@ModelAttribute(USERNAME) String username,
                            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(USERNAME, username)
                .addFlashAttribute("bad_credentials", true);

        return "redirect:login";
    }

    @GetMapping("/login/forgotten-password")
    public String enterEmail() {
        return "auth/enter-email";
    }


    @PostMapping("/login/forgotten-password")
    public String doEnterEmail(@Valid EmailDTO emailDTO, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               HttpSession session) {

        boolean found = userService.findByEmailIfUserExists(emailDTO.getEmail());

        if (!emailDTO.getEmail().isEmpty() && !found) {
            bindingResult.addError(new FieldError("emailDTO", "email",
                    "The email doesn't match an account."));
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("emailDTO", emailDTO)
                    .addFlashAttribute(BINDING_RESULT + "emailDTO", bindingResult);
            return "redirect:/users/login/forgotten-password";
        }
        session.setAttribute("email", emailDTO.getEmail());
        userCodeGenerationService.sendEmailToChangeUserPass(emailDTO);

        return "redirect:/users/login/enter-code";
    }

    @GetMapping("/login/enter-code")
    public String enterCode() {
        return "auth/enter-code";
    }

    @PostMapping("/login/enter-code")
    public String doEnterCode(@Valid CodeDTO codeDTO,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              HttpSession session) {

        String email = (String) session.getAttribute("email");

        PasswordChangeCode code = userCodeGenerationService.findPassCodeByUserEmail(email);


        if(code != null){
            if (!codeDTO.getCode().equals(code.getCode())) {
                bindingResult.addError(new FieldError("codeDTO", "code",
                        "The provided code is not correct."));
            }
            if(bindingResult.hasErrors()){
                redirectAttributes.addFlashAttribute("codeDTO", codeDTO).
                        addFlashAttribute(BINDING_RESULT + "codeDTO", bindingResult);
                return "redirect:/users/login/enter-code";
            }
        } else {
            return "redirect:/users/login/enter-code";
        }

        session.setAttribute("code", codeDTO.getCode());
        return "auth/new-password";
    }


    @GetMapping("/login/new-password")
    public String newPassword() {
        return "auth/new-password";
    }

    @PostMapping("/login/new-password")
    public String doChangePassword(@Valid NewPasswordDTO newPasswordDTO,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes,
                                   HttpSession session) {


        String email = (String) session.getAttribute("email");
        String code = (String) session.getAttribute("code");

        if(bindingResult.hasErrors()){
            redirectAttributes.
                    addFlashAttribute("newPasswordDTO", newPasswordDTO)
                    .addFlashAttribute(BINDING_RESULT + "newPasswordDTO", bindingResult);

            return "redirect:/users/login/new-password";
        }

        boolean changed
                = userService.changePassword(email, code, newPasswordDTO.getNewPassword());

        if(changed){
            redirectAttributes.addFlashAttribute("changed", true);
        }
        return "redirect:/users/login";
    }

    @ModelAttribute
    public EmailDTO emailDTO() {
        return new EmailDTO();
    }


    @ModelAttribute
    public CodeDTO codeDTO() {
        return new CodeDTO();
    }

    @ModelAttribute
    public NewPasswordDTO newPasswordDTO() {
        return new NewPasswordDTO();
    }
}