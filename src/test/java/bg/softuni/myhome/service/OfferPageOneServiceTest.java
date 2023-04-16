package bg.softuni.myhome.service;

import bg.softuni.myhome.model.dto.OfferPageOneDTO;
import bg.softuni.myhome.model.entities.CategoryEntity;
import bg.softuni.myhome.model.entities.OfferPageOne;
import bg.softuni.myhome.model.enums.ConstructionEnum;
import bg.softuni.myhome.model.enums.HeatingEnum;
import bg.softuni.myhome.model.enums.OfferTypeEnum;
import bg.softuni.myhome.repository.OfferPageOneRepository;
import bg.softuni.myhome.util.TestDataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class OfferPageOneServiceTest {

    @Mock
    private OfferPageOneRepository mockOfferPageOneRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @Mock
    private CategoryService mockCategoryService;

    @Mock
    private OfferService mockOfferService;

    private OfferPageOneService testOfferPageOneService;

    @Captor
    private ArgumentCaptor<OfferPageOne> offerPageOneArgumentCaptor;

    @BeforeEach
    void setUp() {
        this.testOfferPageOneService = new OfferPageOneService(mockOfferPageOneRepository,
                modelMapper, mockCategoryService, mockOfferService);
    }

    @Test
    void test_saveOfferPageOne_savedWithCorrectData() {

        OfferPageOneDTO dto = getTestDTO();

        setCategory();

        testOfferPageOneService.saveOfferPageOne(dto);
        verify(mockOfferPageOneRepository).save(offerPageOneArgumentCaptor.capture());
        OfferPageOne actualSaved = offerPageOneArgumentCaptor.getValue();
        assertEquals(dto.getArea(), actualSaved.getArea());
        assertEquals(dto.getConstruction(), actualSaved.getConstruction());
        assertEquals(dto.getCategoryName(), actualSaved.getCategory().getName());
        assertEquals(dto.getHeating(), actualSaved.getHeating());
        assertEquals(dto.getDescription(), dto.getDescription());
        assertEquals(dto.getType(), actualSaved.getType());
        assertEquals(dto.getName(), actualSaved.getName());
        assertEquals(dto.getPrice(), actualSaved.getPrice());

    }

    @Test
    void test_editOfferPageOne_updatesCurrentOfferPageOne(){
        OfferPageOneDTO dto = getTestDTO();
        OfferPageOne pageToEdit = TestDataUtils.getTestOfferPageOne();

        setCategory();
        testOfferPageOneService.editOfferPageOne(pageToEdit, dto);
        verify(mockOfferPageOneRepository).save(offerPageOneArgumentCaptor.capture());
        OfferPageOne actualEditedPageOne = offerPageOneArgumentCaptor.getValue();
        assertEquals(3, actualEditedPageOne.getId());
        assertNotEquals(OfferTypeEnum.SALE, actualEditedPageOne.getType());
        assertEquals(dto.getArea(), actualEditedPageOne.getArea());
    }



    private OfferPageOneDTO getTestDTO() {
        return new OfferPageOneDTO()
                .setArea(BigDecimal.valueOf(1000))
                .setName("testName")
                .setConstruction(ConstructionEnum.BRICK)
                .setHeating(HeatingEnum.TPP)
                .setDescription("testDesc")
                .setCategoryName("testCategory")
                .setPrice(BigDecimal.valueOf(500))
                .setType(OfferTypeEnum.RENT);
    }

    private void setCategory(){
        when(mockCategoryService.findByName("testCategory"))
                .thenReturn(new CategoryEntity()
                .setName("testCategory"));
    }


}
