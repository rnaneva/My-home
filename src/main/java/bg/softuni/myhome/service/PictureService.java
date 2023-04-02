package bg.softuni.myhome.service;

import bg.softuni.myhome.model.dto.OfferAddPageThreeDTO;
import bg.softuni.myhome.model.entities.PictureEntity;
import bg.softuni.myhome.repository.PictureRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class PictureService {

    private OfferService offerService;
    private CloudinaryService cloudinaryService;
    private PictureRepository pictureRepository;

    public PictureService(OfferService offerService, CloudinaryService cloudinaryService, PictureRepository pictureRepository) {
        this.offerService = offerService;
        this.cloudinaryService = cloudinaryService;
        this.pictureRepository = pictureRepository;
    }

    public void savePictures(OfferAddPageThreeDTO offerAddPageThreeDTO, String offerVisibleId){
        offerAddPageThreeDTO.getPictures().forEach(file-> {
            try {
                savePicture(offerVisibleId, file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void savePicture(String offerVisibleId, MultipartFile file) throws IOException {
        String url = cloudinaryService.uploadPicture(file);
        PictureEntity picture = new PictureEntity()
                .setUrl(url)
                .setTitle(file.getName())
                .setOffer(offerService.getOfferByVisibleId(offerVisibleId));
        pictureRepository.save(picture);
    }
}

