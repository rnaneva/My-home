package bg.softuni.myhome.util;

import bg.softuni.myhome.model.entities.*;
import bg.softuni.myhome.model.enums.AvailableEnum;
import bg.softuni.myhome.model.enums.ConstructionEnum;
import bg.softuni.myhome.model.enums.HeatingEnum;
import bg.softuni.myhome.model.enums.OfferTypeEnum;

import java.math.BigDecimal;

public class TestDataUtils {


    public static OfferPageTwo getTestOfferPageTwo() {
        return new OfferPageTwo()
                .setId(3L)
                .setBalconies(1)
                .setLocation(newLocation())
                .setBathrooms(1)
                .setBedrooms(1)
                .setAllFloors(10)
                .setFloor(2)
                .setConstructionYear(2000)
                .setElevator(AvailableEnum.NO)
                .setParking(AvailableEnum.YES);
    }

    public static LocationEntity newLocation() {
        return new LocationEntity().setAddress("testAddress")
                .setCity(new CityEntity().setName("testCity"));
    }

    public static OfferPageOne getTestOfferPageOne(){

        return new OfferPageOne()
                .setId(2L)
                .setArea(BigDecimal.valueOf(2000))
                .setName("testName2")
                .setConstruction(ConstructionEnum.LFW)
                .setHeating(HeatingEnum.GAS)
                .setDescription("testDescription".repeat(20))
                .setCategory(new CategoryEntity().setName("testCategory"))
                .setPrice(BigDecimal.valueOf(3000))
                .setType(OfferTypeEnum.SALE);
    }

}
