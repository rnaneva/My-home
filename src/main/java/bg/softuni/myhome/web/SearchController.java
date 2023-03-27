package bg.softuni.myhome.web;

import bg.softuni.myhome.model.dto.SearchDTO;
import bg.softuni.myhome.model.dto.OfferDTO;
import bg.softuni.myhome.service.OfferService;
import bg.softuni.myhome.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class SearchController {


    private SearchService searchService;
    private OfferService offerService;

    public SearchController(SearchService searchService, OfferService offerService) {
        this.searchService = searchService;
        this.offerService = offerService;
    }


    @GetMapping("/search/{visibleId}")
    public String advancedSearchResult(Model model, @PathVariable String visibleId) {
        SearchDTO searchDTO = searchService.findByVisibleId(visibleId);
        List<OfferDTO> offersFromSearch = offerService.findOffersBySearchForm(searchDTO);
        if(offersFromSearch.isEmpty()){
            model.addAttribute("no_offers", true);
        }
        model.addAttribute("offers", offersFromSearch);

        return "search-form-result";
    }



}
