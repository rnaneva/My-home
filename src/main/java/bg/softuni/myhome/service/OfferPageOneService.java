package bg.softuni.myhome.service;

import bg.softuni.myhome.model.dto.OfferAddPageOneDTO;
import bg.softuni.myhome.model.entities.CategoryEntity;
import bg.softuni.myhome.model.entities.OfferPageOne;
import bg.softuni.myhome.repository.OfferPageOneRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class OfferPageOneService {

    private OfferPageOneRepository offerPageOneRepository;
    private ModelMapper modelMapper;
    private CategoryService categoryService;


    public OfferPageOneService(OfferPageOneRepository offerPageOneRepository, ModelMapper modelMapper, CategoryService categoryService) {

        this.offerPageOneRepository = offerPageOneRepository;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;

    }

    public OfferPageOne saveOfferPageOne( OfferAddPageOneDTO offerAddPageOneDTO) {

        OfferPageOne pageOne = modelMapper.map(offerAddPageOneDTO, OfferPageOne.class);

        CategoryEntity category = categoryService.findByName(offerAddPageOneDTO.getCategoryName());
        pageOne.setCategory(category);

        offerPageOneRepository.save(pageOne);


        return pageOne;

    }


}
