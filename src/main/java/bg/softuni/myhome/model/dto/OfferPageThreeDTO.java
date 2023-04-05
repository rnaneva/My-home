package bg.softuni.myhome.model.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class OfferPageThreeDTO {


    private List<MultipartFile> pictures;

    public OfferPageThreeDTO() {
        this.pictures = new ArrayList<>();
    }

    public List<MultipartFile> getPictures() {
        return pictures;
    }

    public OfferPageThreeDTO setPictures(List<MultipartFile> pictures) {
        this.pictures = pictures;
        return this;
    }
}
