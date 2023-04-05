package bg.softuni.myhome.service;

import bg.softuni.myhome.model.dto.OfferPageThreeDTO;
import bg.softuni.myhome.model.entities.OfferEntity;
import bg.softuni.myhome.model.entities.PictureEntity;
import bg.softuni.myhome.model.view.PictureView;
import bg.softuni.myhome.repository.PictureRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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


//    todo error message to upload less
    public void savePictures(OfferPageThreeDTO offerPageThreeDTO, String offerVisibleId){
        OfferEntity offer = offerService.getOfferByVisibleId(offerVisibleId);
        if(validMaxPicsAllowed(offer, offerPageThreeDTO)){
            offerPageThreeDTO.getPictures().forEach(file-> {
                try {
                    savePicture(offerVisibleId, file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

    }

    public void deleteById(long id){
        pictureRepository.deleteById(id);
    }

    public PictureView getPictureViewById(long id){
        return pictureRepository.findById(id)
                .map(this::toPictureView).orElse(null);
    }

    private boolean validMaxPicsAllowed(OfferEntity offer, OfferPageThreeDTO offerPageThreeDTO){
        return offer.getPictures().size() + offerPageThreeDTO.getPictures().size() <= 15;
    }

    private void savePicture(String offerVisibleId, MultipartFile file) throws IOException {
        String url = cloudinaryService.uploadPicture(file);
        PictureEntity picture = new PictureEntity()
                .setUrl(url)
                .setTitle(file.getOriginalFilename())
                .setOffer(offerService.getOfferByVisibleId(offerVisibleId));
        pictureRepository.save(picture);
    }


//    todo repeat

    private PictureView toPictureView (PictureEntity picture){
        return new PictureView()
                .setId(picture.getId())
                .setUrl(picture.getUrl())
                .setName(picture.getTitle());
    }
}

