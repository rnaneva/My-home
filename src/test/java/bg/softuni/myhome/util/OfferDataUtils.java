package bg.softuni.myhome.util;

import bg.softuni.myhome.model.dto.OfferPageOneDTO;
import bg.softuni.myhome.model.dto.OfferPageThreeDTO;
import bg.softuni.myhome.model.dto.OfferPageTwoDTO;
import bg.softuni.myhome.model.enums.AvailableEnum;
import bg.softuni.myhome.model.enums.ConstructionEnum;
import bg.softuni.myhome.model.enums.HeatingEnum;
import bg.softuni.myhome.model.enums.OfferTypeEnum;
import bg.softuni.myhome.model.view.OfferDetailsView;
import bg.softuni.myhome.model.view.OfferPageTwoView;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public class OfferDataUtils {

    public static OfferPageTwoDTO offerPageTwoDTO() {
        return new OfferPageTwoDTO()
                .setBalconies(2)
                .setAddress("testAddress")
                .setBathrooms(2)
                .setBedrooms(2)
                .setAllFloors(10)
                .setFloor(1)
                .setCityName("TestCity")
                .setConstructionYear(1999)
                .setElevator(AvailableEnum.YES)
                .setParking(AvailableEnum.NO);
    }

    public static OfferPageThreeDTO offerPageThreeDTO() {
        MultipartFile file1 = EntitiesDataUtils.createMultipartFile();
        MultipartFile file2 = EntitiesDataUtils.createMultipartFile();
        return new OfferPageThreeDTO()
                .setPictures(List.of(file1, file2));
    }

    public static OfferPageOneDTO offerPageOneDTO() {
        return new OfferPageOneDTO()
                .setArea(BigDecimal.valueOf(2000))
                .setName("testName2")
                .setConstruction(ConstructionEnum.LFW)
                .setHeating(HeatingEnum.GAS)
                .setDescription("testDescription".repeat(20))
                .setCategoryName("category")
                .setPrice(BigDecimal.valueOf(3000))
                .setType(OfferTypeEnum.SALE);
    }

    public static OfferPageTwoView offerPageTwoView() {
        return new OfferPageTwoView()
                .setBalconies(2)
                .setLocationAddress("adsresss")
                .setBathrooms(2)
                .setBedrooms(2)
                .setAllFloors(10)
                .setFloor(1)
                .setLocationCityName("TestCity")
                .setConstructionYear(1999)
                .setElevator(AvailableEnum.YES)
                .setParking(AvailableEnum.NO);
    }

    public static OfferDetailsView offerDetailsView(){
        return new OfferDetailsView()
                .setName("name")
                .setArea(BigDecimal.ONE)
                .setFloorInfo("floorInfo")
                .setPlan("plan")
                .setDescription("description")
                .setAddress("address")
                .setCity("city")
                .setPrice(BigDecimal.TEN)
                .setAgencyLogoUrl("url")
                .setCreatedOn("date")
                .setVisibleId("visibleId")
                .setCategory(EntitiesDataUtils.addCategory())
                .setConstruction("constr")
                .setConstructionYear(2000)
                .setHeating("heating")
                .setElevator(AvailableEnum.YES)
                .setParking(AvailableEnum.NO)
                .setType(OfferTypeEnum.RENT)
                .setImages(EntitiesDataUtils.addPictures());

    }
}
