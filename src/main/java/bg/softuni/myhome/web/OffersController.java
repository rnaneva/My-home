package bg.softuni.myhome.web;

import bg.softuni.myhome.model.dto.CategoryDTO;
import bg.softuni.myhome.model.dto.CityDTO;
import bg.softuni.myhome.model.dto.OfferDTO;
import bg.softuni.myhome.model.dto.QuickSearchDTO;
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
    public String getAllRentProperties(Model model) {
        List<OfferDTO> offers = offerService.allRentProperties();
        model.addAttribute("rentOffers", offers);
        return "rent-offers";
    }




    @GetMapping("/sale")
    public String getAllSaleProperties(Model model) {
        List<OfferDTO> offers = offerService.allSaleProperties();
        List<CategoryDTO> allCategories = categoryService.getAllCategories();
        List<CityDTO> allCities = cityService.getAllCities();
        model.addAttribute("saleOffers", offers);
        model.addAttribute("categories", allCategories);
        model.addAttribute("cities", allCities);

        return "sale-offers";
    }

    @PostMapping("/sale")
    public String makeSearch(@Valid QuickSearchDTO quickSearchDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("quickSearchDTO", quickSearchDTO)
                    .addFlashAttribute(BINDING_RESULT + "quickSearchDTO", bindingResult);

            return "redirect:sale";
        }

        long id = searchService.saveSearchCriteria(quickSearchDTO);

        return "redirect:/search/7";
    }


    @ModelAttribute
    public OfferDTO offerDTO(){
        return new OfferDTO();
    }

    @ModelAttribute
    public CategoryDTO categoryDTO(){
        return new CategoryDTO();
    }

    @ModelAttribute
    public CityDTO cityDTO(){
        return new CityDTO();
    }

    @ModelAttribute
    public QuickSearchDTO quickSearchDTO() {
        return new QuickSearchDTO();
    }

}
