package bg.softuni.myhome.web;

import bg.softuni.myhome.model.dto.CategoryDTO;
import bg.softuni.myhome.model.dto.CityDTO;
import bg.softuni.myhome.model.dto.EditUserDTO;
import bg.softuni.myhome.model.enums.UserRoleEnum;
import bg.softuni.myhome.model.view.UserView;
import bg.softuni.myhome.service.CategoryService;
import bg.softuni.myhome.service.CityService;
import bg.softuni.myhome.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static bg.softuni.myhome.commons.StaticVariables.*;


@Controller
@RequestMapping("/admin")
public class AdminController {


    private final UserService userService;
    private final CategoryService categoryService;
    private final CityService cityService;

    public AdminController(UserService userService, CategoryService categoryService, CityService cityService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.cityService = cityService;
    }

    @Secured(ROLE_ADMIN)
    @GetMapping
    public String admin(Model model) {

        List<UserView> users = userService.findAllByOrderByLastUpdatedOnDesc();

        model.addAttribute("users", users);

        return "admin/admin";
    }

    @GetMapping("/users/edit/{id}")
    public String getEditUserAccount(@PathVariable long id, Model model) {
        UserView user = userService.getUserViewById(id);
        model.addAttribute("user", user);
        model.addAttribute("userRoleEnums", UserRoleEnum.values());
        return "admin/admin-edit-user";
    }

    @PatchMapping("/users/edit/{id}")
    public String patchEditUserAccount(@PathVariable long id,
                                       @Valid EditUserDTO editUserDTO,
                                       BindingResult bindingResult,
                                       RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("editUserDTO", editUserDTO)
                    .addFlashAttribute(BINDING_RESULT + "editUserDTO", bindingResult);


            return REDIRECT_ADMIN_EDIT + id;

        }
        userService.editUser(editUserDTO);

        return "redirect:/admin";
    }

    @GetMapping("/categories/new")
    public String getNewCategory() {
        return "admin/admin-new-category";
    }


    @GetMapping("/cities/new")
    public String getNewLocation() {
        return "admin/admin-new-city";
    }

    @PostMapping("/categories/new")
    public String postNewCategory(@Valid CategoryDTO categoryDTO,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("categoryDTO", categoryDTO)
                    .addFlashAttribute(BINDING_RESULT + "categoryDTO", bindingResult);

            return "redirect:/admin/categories/new";
        }

        categoryService.saveCategory(categoryDTO);

        return "redirect:/admin";

    }

    @PostMapping("/cities/new")
    public String postNewCity(@Valid CityDTO cityDTO,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("cityDTO", cityDTO)
                    .addFlashAttribute(BINDING_RESULT + "cityDTO", bindingResult);

            return "redirect:/admin/cities/new";
        }

        cityService.saveCity(cityDTO);

        return "redirect:/admin";

    }


    @ModelAttribute
    public EditUserDTO editUserDTO() {
        return new EditUserDTO();
    }

    @ModelAttribute
    public CategoryDTO categoryDTO() {
        return new CategoryDTO();
    }

    @ModelAttribute
    public CityDTO cityDTO() {
        return new CityDTO();
    }
}
