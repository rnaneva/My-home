package bg.softuni.myhome.util;

import bg.softuni.myhome.model.entities.*;
import bg.softuni.myhome.model.enums.*;

import bg.softuni.myhome.model.view.PictureView;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class EntitiesDataUtils {


    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();



    public static OfferPageOne getTestOfferPageOne(){

        return new OfferPageOne()
                .setId(3L)
                .setArea(BigDecimal.valueOf(2000))
                .setName("testName2")
                .setConstruction(ConstructionEnum.LFW)
                .setHeating(HeatingEnum.GAS)
                .setDescription("testDescription".repeat(20))
                .setCategory(addCategory())
                .setPrice(BigDecimal.valueOf(3000))
                .setType(OfferTypeEnum.SALE);
    }


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



    public static OfferEntity getOffer() {
        return new OfferEntity()
                .setCreatedOn(LocalDate.now())
                .setVisibleId("testOfferId")
                .setPictures(addPictures())
                .setAgency(addAgency())
                .setOfferPageTwo(EntitiesDataUtils.getTestOfferPageTwo())
                .setOfferPageOne(EntitiesDataUtils.getTestOfferPageOne())
                .setStatus(StatusEnum.ACTIVE)
                .setId(3L);
    }

    public static LocationEntity newLocation() {
        return new LocationEntity()
                .setId(1L)
                .setAddress("testAddress")
                .setCity(addCity());
    }

    public static CityEntity addCity(){
        return new CityEntity()
                .setId(1L)
                .setName("TestCity");
    }



    public static AgencyEntity addAgency(){
        return new AgencyEntity()
                .setPhoneNumber("testPhone")
                .setAddress("testAddress")
                .setId(1L)
                .setLogoUrl("logoUrl")
                .setName("testAgencyName")
                .setStatus(StatusEnum.ACTIVE)
                .setUser(addUser());
    }

    public static UserEntity addUser(){
        return new UserEntity()
                .setEmail("testEmail")
                .setNames("testNames")
                .setUsername("testUsername")
                .setVisibleId("testVisibleId")
                .setLastUpdatedOn(LocalDate.now())
                .setPassword(passwordEncoder.encode("password"))
                .setId(1L)
                .setRoles(List.of(addRoleModerator()));
    }

    public static UserRoleEntity addRoleUser(){
        return new UserRoleEntity()
                .setId(1L)
                .setRole(UserRoleEnum.USER);
    }
    public static UserRoleEntity addRoleModerator(){
        return new UserRoleEntity()
                .setId(2L)
                .setRole(UserRoleEnum.MODERATOR);
    }

    public static List<PictureEntity> addPictures(){
        List<PictureEntity> pics = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            pics.add(new PictureEntity()
                    .setUrl("url" + i)
                    .setTitle("title" + i)
                    .setId((long) i));

        }
        return pics;
    }

    public static List<PictureView> picturesView(){
       return  addPictures().stream().map(picture -> {
            return new PictureView().setId(picture.getId())
                    .setName(picture.getTitle()).setUrl(picture.getUrl());
        }).collect(Collectors.toList());
    }

    public static CategoryEntity addCategory(){
        return new CategoryEntity()
                .setId(1L)
                .setName("studio");
    }

    public static RequestEntity addRequest(){
        return new RequestEntity()
                .setId(1L)
                .setClientName("TestName")
                .setEmail("testEmail")
                .setStatus(RequestStatusEnum.NEW)
                .setMessage("testMessage")
                .setPhone("testPhone")
                .setReceivedOn(LocalDate.now())
                .setNotes("testNotes")
                .setOffer(getOffer());
    }

    public static MultipartFile createMultipartFile() {
        byte[] image = "src/test/resources/image/Logo.png".getBytes();
        return new MockMultipartFile("file", image);
    }







}
