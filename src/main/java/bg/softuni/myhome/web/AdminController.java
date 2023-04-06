package bg.softuni.myhome.web;

import bg.softuni.myhome.model.dto.EditUserDTO;
import bg.softuni.myhome.model.view.UserView;
import bg.softuni.myhome.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static bg.softuni.myhome.commons.StaticVariables.BINDING_RESULT;


@Controller
public class AdminController {

private UserService userService;

    public AdminController(UserService userService ) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String admin(Model model,
                        @PageableDefault(sort = "updateDate" ,size = 5) Pageable pageable){
//        todo desc
        List<UserView> users = userService.getAllUserViews(pageable);
        model.addAttribute("users", users);
        return "admin";
    }

    @GetMapping("/admin/users/{id}/edit")
    public String editUserAccount(@PathVariable long id, Model model){
        UserView user = userService.getUserViewById(id);
        model.addAttribute("user", user);
        return "admin-edit-user";
    }

    @PatchMapping("/admin/users/{id}/edit")
    public String editUserAccount(@PathVariable long id,
                                  @Valid EditUserDTO editUserDTO,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("editUserDTO", editUserDTO)
                    .addFlashAttribute(BINDING_RESULT + "editUserDTO", bindingResult);


            return "redirect:/admin/users/" + id + "/edit";

        }
        userService.editUser(editUserDTO);

        return "redirect:/admin";
    }

    @ModelAttribute
    public UserView userView(){
        return new UserView();
    }

    @ModelAttribute
    public EditUserDTO editUserDTO(){
        return new EditUserDTO();
    }


}
