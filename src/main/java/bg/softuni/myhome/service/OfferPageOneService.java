package bg.softuni.myhome.service;

import bg.softuni.myhome.model.dto.OfferPageOneDTO;
import bg.softuni.myhome.model.entities.CategoryEntity;
import bg.softuni.myhome.model.entities.OfferPageOne;
import bg.softuni.myhome.model.view.OfferPageOneView;
import bg.softuni.myhome.repository.OfferPageOneRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class OfferPageOneService {

    private final OfferPageOneRepository offerPageOneRepository;
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;
    private final OfferService offerService;


    public OfferPageOneService(OfferPageOneRepository offerPageOneRepository, ModelMapper modelMapper, CategoryService categoryService, OfferService offerService) {

        this.offerPageOneRepository = offerPageOneRepository;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
        this.offerService = offerService;
    }


    public OfferPageOne saveOfferPageOne(OfferPageOneDTO offerPageOneDTO) {

        OfferPageOne pageOne = modelMapper.map(offerPageOneDTO, OfferPageOne.class);

        CategoryEntity category = categoryService.findByName(offerPageOneDTO.getCategoryName());
        pageOne.setCategory(category);

        offerPageOneRepository.save(pageOne);


        return pageOne;

    }

    public void editOfferPageOne(OfferPageOne offerPageOne,
                                         OfferPageOneDTO dto) {

        CategoryEntity category = categoryService.findByName(dto.getCategoryName());

        offerPageOne.setArea(dto.getArea())
               .setName(dto.getName())
               .setDescription(dto.getDescription())
               .setPrice(dto.getPrice())
               .setHeating(dto.getHeating())
               .setType(dto.getType())
               .setCategory(category)
                       .setConstruction(dto.getConstruction());


        offerPageOneRepository.save(offerPageOne);

    }

    public OfferPageOneView getOfferPageOneViewByVisibleId(String visibleId){
      return  modelMapper.map(getOfferPageOneByOfferVisibleId(visibleId), OfferPageOneView.class);

    }


    public OfferPageOne getOfferPageOneByOfferVisibleId(String visibleId){
        return offerService.getOfferById(visibleId).getOfferPageOne();
    }


}
