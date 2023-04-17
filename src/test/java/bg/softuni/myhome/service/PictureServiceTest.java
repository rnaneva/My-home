package bg.softuni.myhome.service;

import bg.softuni.myhome.exception.ObjectNotFoundException;
import bg.softuni.myhome.model.dto.OfferPageThreeDTO;
import bg.softuni.myhome.model.entities.OfferEntity;
import bg.softuni.myhome.model.entities.PictureEntity;
import bg.softuni.myhome.model.view.PictureView;
import bg.softuni.myhome.repository.PictureRepository;
import bg.softuni.myhome.util.EntitiesDataUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class PictureServiceTest {

    @Mock
    private OfferService mockOfferService;

    @Mock
    private CloudinaryService mockCloudinaryService;

    @Mock
    private PictureRepository mockPictureRepository;

    @InjectMocks
    private PictureService testPictureService;

    @Captor
    private ArgumentCaptor<PictureEntity> pictureEntityArgumentCaptor;

    @Test
    void test_savePictures_whenValidFilesSavedToRepository() throws IOException {
        String offerVisibleId = "1114";

        OfferEntity offer = new OfferEntity().setVisibleId(offerVisibleId);

        OfferPageThreeDTO dto = offerPageThreeDTO();

        when(mockOfferService.getOfferById(offerVisibleId)).thenReturn(offer);
        when(mockCloudinaryService.uploadPicture(dto.getPictures().get(0))).thenReturn("url1");
        when(mockCloudinaryService.uploadPicture(dto.getPictures().get(1))).thenReturn("url2");

        testPictureService.savePictures(dto, offerVisibleId);
        verify(mockPictureRepository, times(2)).save(pictureEntityArgumentCaptor.capture());
        List<PictureEntity> pics = pictureEntityArgumentCaptor.getAllValues();
        assertEquals(2, pics.size());
        assertEquals("url1", pics.get(0).getUrl());
        assertEquals("url2", pics.get(1).getUrl());
        assertEquals(offerVisibleId, pics.get(0).getOffer().getVisibleId());

    }

    @Test
    void test_savePictures_pictureNotFoundWillThrow() {
        assertThrows(NullPointerException.class,
                () -> testPictureService.savePictures(new OfferPageThreeDTO(), "not_existing"));
    }

    @Test
    void test_deleteById_PictureFoundAndDeleted_ReturnsTrue() {
        long pictureId = 1L;
        when(mockPictureRepository.findById(pictureId))
                .thenReturn(Optional.of(new PictureEntity().setId(pictureId)));

        boolean isDeleted = testPictureService.deleteById(pictureId);
        assertTrue(isDeleted);
        verify(mockPictureRepository).deleteById(pictureId);

    }

    @Test
    void test_deleteById_PictureNotFound_ReturnsFalse() {
        assertFalse(testPictureService.deleteById(1L));
        verify(mockPictureRepository, never()).deleteById(1L);

    }

    @Test
    void test_getPictureViewById_PictureNotFoundThrows(){
        assertThrows(ObjectNotFoundException.class, () ->
                testPictureService.getPictureViewById(1L));
    }


    @Test
    void test_getPictureViewById_Found_ReturnsPictureView(){
        Long id = 1L;
        String url = "url1";
        PictureEntity picture = new PictureEntity().setId(id).setUrl(url);
        when(mockPictureRepository.findById(id))
                .thenReturn(Optional.ofNullable(picture));

        PictureView foundPicture = testPictureService.getPictureViewById(id);
        assertEquals(id, foundPicture.getId());
        assertEquals(url, foundPicture.getUrl());
    }

    private OfferPageThreeDTO offerPageThreeDTO() {
        MultipartFile file1 = EntitiesDataUtils.createMultipartFile();
        MultipartFile file2 = EntitiesDataUtils.createMultipartFile();
        return new OfferPageThreeDTO()
                .setPictures(List.of(file1, file2));
    }


}
