package bg.softuni.myhome.web;

import bg.softuni.myhome.model.dto.UserRegisterDTO;
import bg.softuni.myhome.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static bg.softuni.myhome.commons.StaticVariables.*;

@Controller
@RequestMapping("/users")
public class RegisterController {


    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;

    }

    @GetMapping("/register")
    public String getRegister() {
        return "auth/register";
    }

    @GetMapping("/{userId}/activate/{activationCode}")
    public String activateAccount(@PathVariable String userId, @PathVariable String activationCode) {

        boolean codeIsValid = userService.activateUserProfile(userId, activationCode);
        if (codeIsValid) {
            return "redirect:/users/login";
        }
        return "expired-link";
    }


    @PostMapping("/register")
    public String postRegister(@Valid UserRegisterDTO userRegisterDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {


        if (!userService.passwordsMatch(userRegisterDTO)) {
            bindingResult.addError(new FieldError("userRegisterDTO", "confirmPassword",
                    "Passwords should match"));
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterDTO", userRegisterDTO)
                    .addFlashAttribute(BINDING_RESULT + "userRegisterDTO", bindingResult);

            return "redirect:register";
        }

        userService.registerUser(userRegisterDTO);
        return "successful-registration";

    }

    @ModelAttribute
    public UserRegisterDTO userRegisterDTO() {
        return new UserRegisterDTO();
    }
}
