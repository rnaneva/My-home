package bg.softuni.myhome.service;

import bg.softuni.myhome.model.dto.SearchDTO;
import bg.softuni.myhome.model.entities.CategoryEntity;
import bg.softuni.myhome.model.entities.CityEntity;
import bg.softuni.myhome.model.entities.SearchEntity;
import bg.softuni.myhome.repository.SearchRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class SearchService {

    private SearchRepository searchRepository;
    private CityService cityService;
    private CategoryService categoryService;
    private AgencyService agencyService;
    private ModelMapper modelMapper;

    public SearchService(SearchRepository searchRepository, CityService cityService,
                         CategoryService categoryService, AgencyService agencyService,
                         ModelMapper modelMapper) {

        this.searchRepository = searchRepository;
        this.cityService = cityService;
        this.categoryService = categoryService;
        this.agencyService = agencyService;
        this.modelMapper = modelMapper;
    }





    public String saveSearchCriteria(SearchDTO dto) {

        CityEntity city = cityService.findByName(dto.getCityName());
        CategoryEntity category = categoryService.findByName(dto.getCategoryName());

        SearchEntity search = modelMapper.map(dto, SearchEntity.class);
        search.setCategory(category)
//                .setType(dto.getType())
                .setCity(city);

        if (dto.getAgencyName() != null) {
            search.setAgency(agencyService.findByName(dto.getAgencyName()));
        }

        Random rnd = new Random();
        int part  = rnd.nextInt(Integer.MAX_VALUE);
        search.setVisibleId("s1f5ch" + part);
//
//        if(dto.getConstruction() != null){
//            search.setConstruction(dto.getConstruction());
//        }
//        if(dto.getHeating() != null)

        return searchRepository.save(search).getVisibleId();

    }

    public SearchDTO findById(String visibleId) {
        Optional<SearchEntity> optSearch = searchRepository.findByVisibleId(visibleId);
        return optSearch.map(search -> modelMapper.map(search, SearchDTO.class))
                .orElse(new SearchDTO());
    }





}
