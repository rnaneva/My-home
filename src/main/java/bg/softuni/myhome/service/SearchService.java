package bg.softuni.myhome.service;

import bg.softuni.myhome.model.dto.AdvancedSearchDTO;
import bg.softuni.myhome.model.dto.QuickSearchDTO;
import bg.softuni.myhome.model.entities.CategoryEntity;
import bg.softuni.myhome.model.entities.CityEntity;
import bg.softuni.myhome.model.entities.OfferEntity;
import bg.softuni.myhome.model.entities.SearchEntity;
import bg.softuni.myhome.repository.OfferRepository;
import bg.softuni.myhome.repository.SearchRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SearchService {

    private SearchRepository searchRepository;
    private CityService cityService;
    private CategoryService categoryService;

    public SearchService(SearchRepository searchRepository, CityService cityService, CategoryService categoryService) {

        this.searchRepository = searchRepository;
        this.cityService = cityService;
        this.categoryService = categoryService;
    }


    public long saveSearchCriteria(QuickSearchDTO dto){

        CityEntity city = cityService.findByName(dto.getCity());
        CategoryEntity category = categoryService.findByName(dto.getCategory());

        return  searchRepository.save(new SearchEntity()
                .setCity(city)
                .setCategory(category)
                .setType(dto.getType()))
                .getId();
    }





    public QuickSearchDTO findById(long id){
        return searchRepository.findById(id)
                .map(this::toQuickSearchDTO)
                .orElse(null);
    }

    private QuickSearchDTO toQuickSearchDTO(SearchEntity search){
        return new QuickSearchDTO()
                .setCategory(search.getCategory().getName())
                .setCity(search.getCity().getName())
                .setType(search.getType());
    }
}
