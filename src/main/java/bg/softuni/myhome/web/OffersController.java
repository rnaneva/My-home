package bg.softuni.myhome.web;

import bg.softuni.myhome.model.dto.*;
import bg.softuni.myhome.service.CategoryService;
import bg.softuni.myhome.service.CityService;
import bg.softuni.myhome.service.OfferService;
import bg.softuni.myhome.service.SearchService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static bg.softuni.myhome.staticVariables.StaticVariables.BINDING_RESULT;

@Controller
@RequestMapping("/offers")
public class OffersController {


    private final OfferService offerService;
    private final CategoryService categoryService;
    private final CityService cityService;
    private final SearchService searchService;

    public OffersController(OfferService offerService, CategoryService categoryService,
                            CityService cityService, SearchService searchService) {
        this.offerService = offerService;
        this.categoryService = categoryService;
        this.cityService = cityService;
        this.searchService = searchService;
    }


    @GetMapping("/rent")
    public String getRent(Model model) {
        List<OfferDTO> offers = offerService.allRentProperties();
        List<String> allCategoryNames = categoryService.getAllCategoryNames();
        List<String> allCityNames = cityService.getAllCityNames();
        model.addAttribute("rentOffers", offers);
        model.addAttribute("categories", allCategoryNames);
        model.addAttribute("cities", allCityNames);

        return "rent-offers";
    }

//todo pageable
    @PostMapping("/rent")
    public String postRentSearch(@Valid @ModelAttribute("searchDTO") SearchDTO searchDTO,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("searchDTO", searchDTO)
                    .addFlashAttribute(BINDING_RESULT + "searchDTO", bindingResult);

            return "redirect:rent";
        }

        String visibleId = searchService.saveSearchCriteria(searchDTO);

        return "redirect:/search/" + visibleId;
    }


    @GetMapping("/sale")
    public String getSale(Model model) {
        List<OfferDTO> offers = offerService.allSaleProperties();
        List<String> allCategoryNames = categoryService.getAllCategoryNames();
        List<String> allCityNames = cityService.getAllCityNames();
        model.addAttribute("saleOffers", offers);
        model.addAttribute("categories", allCategoryNames);
        model.addAttribute("cities", allCityNames);

        return "sale-offers";
    }

    //todo pageable
    @PostMapping("/sale")
    public String postSaleSearch(@Valid @ModelAttribute("searchDTO") SearchDTO searchDTO,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("searchDTO", searchDTO)
                    .addFlashAttribute(BINDING_RESULT + "searchDTO", bindingResult);

            return "redirect:sale";
        }

        String visibleId = searchService.saveSearchCriteria(searchDTO);

        return "redirect:/search/" + visibleId;
    }


    @ModelAttribute
    public SearchDTO searchDTO() {
        return new SearchDTO();
    }

}
