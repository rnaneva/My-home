package bg.softuni.myhome.service;

import bg.softuni.myhome.commons.CommonService;
import bg.softuni.myhome.model.AppUserDetails;
import bg.softuni.myhome.model.dto.SearchFormDTO;
import bg.softuni.myhome.model.entities.CategoryEntity;
import bg.softuni.myhome.model.entities.CityEntity;
import bg.softuni.myhome.model.entities.SearchEntity;
import bg.softuni.myhome.model.entities.UserEntity;
import bg.softuni.myhome.repository.SearchRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SearchService {

    private final SearchRepository searchRepository;
    private final CityService cityService;
    private final CategoryService categoryService;
    private final AgencyService agencyService;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final CommonService commonService;

    @Autowired
    public SearchService(SearchRepository searchRepository, CityService cityService,
                         CategoryService categoryService, AgencyService agencyService,
                         ModelMapper modelMapper, UserService userService, CommonService commonService) {

        this.searchRepository = searchRepository;
        this.cityService = cityService;
        this.categoryService = categoryService;
        this.agencyService = agencyService;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.commonService = commonService;
    }




    public String saveSearchCriteria(SearchFormDTO dto, AppUserDetails appUserDetails) {

        CityEntity city = cityService.findByName(dto.getCityName());
        CategoryEntity category = categoryService.findByName(dto.getCategoryName());

        SearchEntity search = modelMapper.map(dto, SearchEntity.class);
        search.setCategory(category)
                .setCity(city);

        if (dto.getAgencyName() != null) {
            search.setAgency(agencyService.findByName(dto.getAgencyName()));
        }

//        "anonymousUser"
        if (appUserDetails != null){
            UserEntity user = userService.findById(appUserDetails.getId());
            search.setUser(user);
        }

        search.setVisibleId(commonService.createVisibleId());

        return searchRepository.save(search).getVisibleId();

    }

    public SearchFormDTO findByVisibleId(String visibleId) {
        Optional<SearchEntity> optSearch = searchRepository.findByVisibleId(visibleId);
        return optSearch.map(search -> modelMapper.map(search, SearchFormDTO.class))
                .orElse(null);
    }







}
