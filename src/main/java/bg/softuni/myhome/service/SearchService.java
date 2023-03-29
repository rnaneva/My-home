package bg.softuni.myhome.service;

import bg.softuni.myhome.model.AppUserDetails;
import bg.softuni.myhome.model.dto.SearchFormDTO;
import bg.softuni.myhome.model.entities.CategoryEntity;
import bg.softuni.myhome.model.entities.CityEntity;
import bg.softuni.myhome.model.entities.SearchEntity;
import bg.softuni.myhome.model.entities.UserEntity;
import bg.softuni.myhome.repository.SearchRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SearchService {

    private final SearchRepository searchRepository;
    private final CityService cityService;
    private final CategoryService categoryService;
    private final AgencyService agencyService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public SearchService(SearchRepository searchRepository, CityService cityService,
                         CategoryService categoryService, AgencyService agencyService,
                         ModelMapper modelMapper, UserService userService) {

        this.searchRepository = searchRepository;
        this.cityService = cityService;
        this.categoryService = categoryService;
        this.agencyService = agencyService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }




    public String saveSearchCriteria(SearchFormDTO dto) {

        CityEntity city = cityService.findByName(dto.getCityName());
        CategoryEntity category = categoryService.findByName(dto.getCategoryName());

        SearchEntity search = modelMapper.map(dto, SearchEntity.class);
        search.setCategory(category)
                .setCity(city);

        if (dto.getAgencyName() != null) {
            search.setAgency(agencyService.findByName(dto.getAgencyName()));
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != "anonymousUser"){
            long id = ((AppUserDetails)principal).getId();
            UserEntity user = userService.findById(id);
            search.setUser(user);
        }

        search.setVisibleId(createVisibleId());

        return searchRepository.save(search).getVisibleId();

    }

    public SearchFormDTO findByVisibleId(String visibleId) {
        Optional<SearchEntity> optSearch = searchRepository.findByVisibleId(visibleId);
        return optSearch.map(search -> modelMapper.map(search, SearchFormDTO.class))
                .orElse(null);
    }

    private String createVisibleId(){
        return UUID.randomUUID().toString();
    }





}
