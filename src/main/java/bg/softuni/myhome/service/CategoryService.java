package bg.softuni.myhome.service;

import bg.softuni.myhome.model.dto.CategoryDTO;
import bg.softuni.myhome.model.view.CategoryView;
import bg.softuni.myhome.model.entities.CategoryEntity;
import bg.softuni.myhome.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public List<CategoryView> getAllCategories(){
        return categoryRepository.findAll()
                .stream()
                .map(this::toCategoryDTO)
                .toList();
    }

    private CategoryView toCategoryDTO(CategoryEntity category){
        return modelMapper.map(category, CategoryView.class);
    }

    public CategoryEntity findByName(String name){
       return categoryRepository.findByName(name).orElse(null);
    }

    public List<String> getAllCategoryNames(){
        return categoryRepository.getAllCategoryNames();
    }

    public void saveCategory(CategoryDTO categoryDTO) {
        categoryRepository.save(
                new CategoryEntity()
                        .setName(categoryDTO.getName()));
    }
}
