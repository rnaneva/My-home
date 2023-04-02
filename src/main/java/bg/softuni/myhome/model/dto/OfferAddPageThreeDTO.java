package bg.softuni.myhome.model.dto;

import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class OfferAddPageThreeDTO {


    private List<MultipartFile> pictures;

    public OfferAddPageThreeDTO() {
        this.pictures = new ArrayList<>();
    }

    public List<MultipartFile> getPictures() {
        return pictures;
    }

    public OfferAddPageThreeDTO setPictures(List<MultipartFile> pictures) {
        this.pictures = pictures;
        return this;
    }
}
