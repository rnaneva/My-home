package bg.softuni.myhome.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class OfferPageThreeDTO {


    @NotEmpty
    private List<MultipartFile> pictures;


    public List<MultipartFile> getPictures() {
        return pictures;
    }

    public OfferPageThreeDTO setPictures(List<MultipartFile> pictures) {
        this.pictures = pictures;
        return this;
    }
}
