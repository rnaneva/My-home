package bg.softuni.myhome.web;

import bg.softuni.myhome.model.dto.EmailDTO;
import bg.softuni.myhome.model.dto.SearchFormDTO;
import bg.softuni.myhome.model.view.OfferView;
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

import static bg.softuni.myhome.commons.StaticVariables.BINDING_RESULT;

@Controller
public class SearchController {


    private final SearchService searchService;
    private final OfferService offerService;

    public SearchController(SearchService searchService, OfferService offerService) {
        this.searchService = searchService;
        this.offerService = offerService;
    }


    @GetMapping("/search/{visibleId}")
    public String searchResult(Model model, @PathVariable String visibleId) {
        SearchFormDTO searchFormDTO = searchService.findSearchByVisibleId(visibleId);
        List<OfferView> offersFromSearch = offerService.findOffersBySearchForm(searchFormDTO);


        if (offersFromSearch.isEmpty()) {
            model.addAttribute("no_offers", true);
        } else {
            model.addAttribute("offers", offersFromSearch);
        }
        model.addAttribute("search_id", visibleId);

        return "search-form-result";
    }

    @PostMapping("/search/{searchId}")
    public String subscribeUser(@PathVariable String searchId,
                                @Valid EmailDTO emailDTO,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("emailDTO", emailDTO)
                    .addFlashAttribute(BINDING_RESULT + "emailDTO", bindingResult);

            return "redirect:/search/" + searchId;
        }

        searchService.setEmail(searchId, emailDTO);

        return "successful-message";
    }

    @ModelAttribute
    public EmailDTO emailDTO() {
        return new EmailDTO();
    }

}
