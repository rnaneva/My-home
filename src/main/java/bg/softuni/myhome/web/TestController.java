package bg.softuni.myhome.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


public class TestController {

//    todo FE validation
//    todo schedular - delete search without user / send email for search with user


    @GetMapping("add/offer/1")
    public String addOffer1(){
        return "add-offer-one";
    }


    @GetMapping("add/offer/2")
    public String addOffer2(){
        return "add-offer-two";
    }

    @GetMapping("add/offer/3")
    public String addOffer3(){
        return "add-offer-three";
    }


    @GetMapping("/users/admin/offers")
    public String adminOffers(){
        return "agency-offers";
    }

    @GetMapping("/agencies")
    public String agencies(){
        return "all-agencies";
    }


    @GetMapping("users/admin/profile/create")
    public String createAgencyProfile(){
        return "create-agency-profile";
    }

    @GetMapping("users/admin/profile/edit")
    public String editAgencyProfile(@PathVariable long id){
        return "edit-agency-profile";
    }

    @GetMapping("offers/edit/1")
    public String editOffer1(@PathVariable long id){
        return "edit-offer-1";
    }

    @GetMapping("offers/edit/2")
    public String editOffer2(@PathVariable long id){
        return "edit-offer-2";
    }

    @GetMapping("offers/edit/3")
    public String editOffer3(@PathVariable long id){
        return "edit-offer-3";
    }


    @GetMapping("/users/moderator/account/edit")
    public String moderatorEditAgencyAccount(@PathVariable long id){
        return "admin-edit-agency";
    }


    @GetMapping("/users/moderator/category/new")
    public String newCategory(){
        return "new-category";
    }


    @GetMapping("/users/moderator/location/new")
    public String newLocation(){
        return "new-category";
    }


    @GetMapping("/admin/requests")
    public String requests(){
        return "agency-requests";
    }

    @GetMapping("/admin/request")
    public String request(@PathVariable long id){
        return "request-details";
    }




}
