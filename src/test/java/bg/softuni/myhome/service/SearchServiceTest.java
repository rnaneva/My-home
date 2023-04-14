package bg.softuni.myhome.service;

import bg.softuni.myhome.exception.ObjectNotFoundException;
import bg.softuni.myhome.model.dto.EmailDTO;
import bg.softuni.myhome.model.dto.SearchFormDTO;
import bg.softuni.myhome.model.entities.CategoryEntity;
import bg.softuni.myhome.model.entities.CityEntity;
import bg.softuni.myhome.model.entities.SearchEntity;
import bg.softuni.myhome.model.enums.OfferTypeEnum;
import bg.softuni.myhome.repository.SearchRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

    @Mock
    private SearchRepository mockSearchRepository;
    @Mock
    private CityService mockCityService;
    @Mock
    private CategoryService mockCategoryService;
    @Mock
    private AgencyService mockAgencyService;
    private final ModelMapper modelMapper = new ModelMapper();
    @Mock
    private UserService mockUserService;
    private SearchService testSearchService;
    @Captor
    private ArgumentCaptor<SearchEntity> searchEntityArgumentCaptor;


    @BeforeEach
    void setUp() {
        this.testSearchService = new SearchService(mockSearchRepository, mockCityService, mockCategoryService,
                mockAgencyService, modelMapper, mockUserService);
    }

    @Test
    void test_saveSearchCriteria_saveInvoked() {
        SearchFormDTO dto = searchFormDTO();
        when(mockCityService.findByName("testCity")).thenReturn(new CityEntity().setName("testCity"));
        when(mockCategoryService.findByName("testCategory")).thenReturn(new CategoryEntity().setName("testCategory"));

        testSearchService.saveSearchCriteria(dto, null);
        verify(mockSearchRepository).save(searchEntityArgumentCaptor.capture());
        SearchEntity search = searchEntityArgumentCaptor.getValue();
        assertEquals("testCity", search.getCity().getName());
        assertEquals("testCategory", search.getCategory().getName());
        assertNull(search.getUser());

    }

    @Test
    void test_findSearchByVisibleId_SearchFound_ReturnsSearchDTO() {
        String visibleId = "searchId";
        SearchEntity search = searchEntity();
        mockFindByVisibleId(visibleId, search);

        SearchFormDTO dto = testSearchService.findSearchByVisibleId(visibleId);
        assertEquals(visibleId, dto.getVisibleId());
        assertEquals(search.getId(), dto.getId());
        assertEquals(search.getCity().getName(), dto.getCityName());

    }

    @Test
    void test_findSearchByVisibleId_SearchNotFound_Throws() {

        assertThrows(ObjectNotFoundException.class,
                () -> testSearchService.findSearchByVisibleId("not_existing"));
    }

    @Test
    void test_setEmail_SearchFound_updated() {
        String visibleId = "searchId";
        EmailDTO emailDto = new EmailDTO().setEmail("testEmail");
        SearchEntity search = searchEntity();
        mockFindByVisibleId(visibleId, search);

        testSearchService.setEmail(visibleId, emailDto);

        verify(mockSearchRepository).save(searchEntityArgumentCaptor.capture());
        SearchEntity updatedSearch = searchEntityArgumentCaptor.getValue();
        assertEquals(emailDto.getEmail(), updatedSearch.getEmail());


    }

    @Test
    void test_setEmail_SearchNotFound_NothingHappens() {
        String visibleId = "searchId";
        EmailDTO emailDto = new EmailDTO().setEmail("testEmail");
        testSearchService.setEmail(visibleId, emailDto);
        verify(mockSearchRepository, never()).save(searchEntityArgumentCaptor.capture());

    }

    @Test
    void test_findSearchesWithRequestForOffers_RequestsFound_ReturnsListOfSearchDTO(){

        SearchEntity search1 = searchEntity().setEmail("email1");
        SearchEntity search2 = searchEntity().setEmail("email2");
        when(mockSearchRepository.findByEmailNotNull()).thenReturn(List.of(search1, search2));
        List<SearchFormDTO> found = testSearchService.findSearchesWithRequestForOffers();
        assertEquals(2, found.size());
        assertEquals("email1", found.get(0).getEmail());
        assertEquals("email2", found.get(1).getEmail());
    }

    @Test
    void test_deleteAllSearchesWithoutEmail_SearchesFound_Deleted(){
        SearchEntity search1 = searchEntity();
        SearchEntity search2 = searchEntity();
        when(mockSearchRepository.findByEmailNull()).thenReturn(List.of(search1, search2));
        testSearchService.deleteAllSearchesWithoutEmail();
        verify(mockSearchRepository).deleteAll(anyCollection());
     }

     @Test
    void test_deleteAllSearchesWithoutEmail_SearchesNotFound_NothingHappens(){
        testSearchService.deleteAllSearchesWithoutEmail();
        verify(mockSearchRepository, never()).deleteAll();

    }

    private SearchFormDTO searchFormDTO() {
        return new SearchFormDTO()
                .setCategoryName("testCategory")
                .setCityName("testCity")
                .setType(OfferTypeEnum.SALE)
                .setSortBy("price");
    }

    private SearchEntity searchEntity() {
        return new SearchEntity()
                .setCategory(new CategoryEntity().setName("testCategory"))
                .setCity(new CityEntity().setName("testCity"))
                .setType(OfferTypeEnum.SALE)
                .setSortBy("price")
                .setId(1L)
                .setVisibleId("searchId");
    }

    private void mockFindByVisibleId(String visibleId, SearchEntity search) {
        when(mockSearchRepository.findByVisibleId(visibleId))
                .thenReturn(Optional.ofNullable(search));
    }
}
