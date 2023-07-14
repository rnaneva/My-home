package bg.softuni.myhome.service;

import bg.softuni.myhome.exception.ObjectNotFoundException;
import bg.softuni.myhome.model.dto.OfferPageThreeDTO;
import bg.softuni.myhome.model.entities.OfferEntity;
import bg.softuni.myhome.model.entities.PictureEntity;
import bg.softuni.myhome.model.view.PictureView;
import bg.softuni.myhome.repository.PictureRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class PictureService {

    private final OfferService offerService;
    private final CloudinaryService cloudinaryService;
    private final PictureRepository pictureRepository;

    public PictureService(OfferService offerService, CloudinaryService cloudinaryService,
                          PictureRepository pictureRepository) {
        this.offerService = offerService;
        this.cloudinaryService = cloudinaryService;
        this.pictureRepository = pictureRepository;
    }



    public void savePictures(OfferPageThreeDTO offerPageThreeDTO, String offerVisibleId){

        OfferEntity offer = offerService.getOfferById(offerVisibleId);

        if(validMaxPicsAllowed(offerPageThreeDTO)){
            offerPageThreeDTO.getPictures().forEach(file-> {
                try {
                    savePicture(offer, file);
                } catch (IOException e) {
                    throw new NullPointerException("File is empty");
                }
            });
        }

    }

    public boolean deleteById(long id){

      Optional<PictureEntity> optPicture = pictureRepository.findById(id);
      if(optPicture.isPresent()){
          pictureRepository.deleteById(id);
          return true;
      }
        return false;
    }

    public PictureView getPictureViewById(long id){
        return pictureRepository.findById(id)
                .map(this::toPictureView)
                .orElseThrow(() -> new ObjectNotFoundException("Picture not found", id));
    }


    private boolean validMaxPicsAllowed( OfferPageThreeDTO offerPageThreeDTO){

        return offerPageThreeDTO.getPictures().size() <= 10;
    }

    private void savePicture(OfferEntity offer, MultipartFile file) throws IOException {
        String url = cloudinaryService.uploadPicture(file);
        PictureEntity picture = new PictureEntity()
                .setUrl(url)
                .setTitle(file.getOriginalFilename())
                .setOffer(offer);
        pictureRepository.save(picture);
    }



    private PictureView toPictureView (PictureEntity picture){
        return new PictureView()
                .setId(picture.getId())
                .setUrl(picture.getUrl())
                .setName(picture.getTitle());
    }



}

