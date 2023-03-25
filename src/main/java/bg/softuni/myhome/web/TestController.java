package bg.softuni.myhome.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


public class TestController {

    @GetMapping("add/offer/1")
    public String addOffer1(){
        return "add-offer-1";
    }


    @GetMapping("add/offer/2")
    public String addOffer2(){
        return "add-offer-2";
    }

    @GetMapping("add/offer/3")
    public String addOffer3(){
        return "add-offer-3";
    }


    @GetMapping("/users/admin/offers")
    public String adminOffers(){
        return "agency-offers";
    }

    @GetMapping("/agencies")
    public String agencies(){
        return "all-agencies";
    }

    @GetMapping("/offers")
    public String offers(){
        return "rent-offers";
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

    @GetMapping("/offer")
    public String offer(@PathVariable long id){
        return "offer-details";
    }

    @GetMapping("/users/register")
    public String register(){
        return "register";
    }

    @GetMapping("/admin/requests")
    public String requests(){
        return "requests";
    }

    @GetMapping("/admin/request")
    public String request(@PathVariable long id){
        return "request-details";
    }


    @GetMapping("/offers/search")
    public String search(){
        return "quick-search-result";
    }


}
