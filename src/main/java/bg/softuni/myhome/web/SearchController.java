package bg.softuni.myhome.web;

import bg.softuni.myhome.model.dto.OfferDTO;
import bg.softuni.myhome.model.dto.QuickSearchDTO;
import bg.softuni.myhome.service.OfferService;
import bg.softuni.myhome.service.SearchService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static bg.softuni.myhome.staticVariables.StaticVariables.BINDING_RESULT;

@Controller
public class SearchController {


    private SearchService searchService;
    private OfferService offerService;

    public SearchController(SearchService searchService, OfferService offerService) {
        this.searchService = searchService;
        this.offerService = offerService;
    }


    @GetMapping("/search/{id}")
    public String quickSearchResult(Model model, @PathVariable long id) {
        QuickSearchDTO searchDTO = searchService.findById(id);
        List<OfferDTO> offersQuickSearch = offerService.findOffersQuickSearch(searchDTO);
        if(offersQuickSearch.isEmpty()){
            model.addAttribute("noOffers", true);
        }
        model.addAttribute("offers", offersQuickSearch);

        return "quick-search-result";
    }




}
