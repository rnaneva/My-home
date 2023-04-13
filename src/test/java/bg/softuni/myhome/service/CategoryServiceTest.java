package bg.softuni.myhome.service;

import bg.softuni.myhome.model.dto.CategoryDTO;
import bg.softuni.myhome.model.entities.CategoryEntity;
import bg.softuni.myhome.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository mockCategoryRepository;

    private CategoryService testCategoryService;

    @Captor
    private ArgumentCaptor<CategoryEntity> categoryEntityArgumentCaptor;

    @BeforeEach
    void setUp(){
        testCategoryService = new CategoryService(mockCategoryRepository);
    }

    @Test
    void test_SaveCategory_SaveInvoked(){
        String expectedName = "testCategory";
        CategoryDTO categoryDTO = new CategoryDTO()
                .setName(expectedName);

        testCategoryService.saveCategory(categoryDTO);

        verify(mockCategoryRepository).save(categoryEntityArgumentCaptor.capture());
        String actualName = categoryEntityArgumentCaptor.getValue().getName();
        assertEquals(expectedName, actualName);

    }
}
