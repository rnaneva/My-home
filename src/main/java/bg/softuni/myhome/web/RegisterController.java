package bg.softuni.myhome.web;

import bg.softuni.myhome.model.dto.UserRegisterDTO;
import bg.softuni.myhome.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static bg.softuni.myhome.staticVariables.StaticVariables.*;

@Controller
@RequestMapping("/users")
public class RegisterController {


    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;

    }

    @GetMapping("/register")
    public String getRegister() {
        return "register";
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
        return "redirect:/users/login";

    }

    @ModelAttribute
    public UserRegisterDTO userRegisterDTO(){
        return new UserRegisterDTO();
    }
}
