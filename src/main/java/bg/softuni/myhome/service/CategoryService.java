package bg.softuni.myhome.service;

import bg.softuni.myhome.model.dto.CategoryDTO;
import bg.softuni.myhome.model.dto.CityDTO;
import bg.softuni.myhome.model.entities.CategoryEntity;
import bg.softuni.myhome.model.entities.CityEntity;
import bg.softuni.myhome.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public CategoryService(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public List<CategoryDTO> getAllCategories(){
        return categoryRepository.findAll()
                .stream()
                .map(this::toCategoryDTO)
                .toList();
    }

    private CategoryDTO toCategoryDTO(CategoryEntity category){
        return modelMapper.map(category, CategoryDTO.class);
    }

    public CategoryEntity findByName(String name){
       return categoryRepository.findByName(name).orElse(null);
    }

    public List<String> getAllCategoryNames(){
        return categoryRepository.getAllCategoryNames();
    }

}
