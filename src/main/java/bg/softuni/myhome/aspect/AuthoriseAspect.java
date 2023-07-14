package bg.softuni.myhome.aspect;

import bg.softuni.myhome.exception.UserNotAuthorizedException;
import bg.softuni.myhome.model.AppUserDetails;

import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;

@Aspect
@Configuration
public class AuthoriseAspect {

    @Pointcut("execution(* bg.softuni.myhome.web.AgencyOffersController.*(..))")
    void allAgencyOffersControllerMethods(){}

    @Pointcut("execution(* bg.softuni.myhome.web.AgencyRequestsController.*(..))")
    void agencyRequestsControllerGetMethods(){}

    @Pointcut("execution(* bg.softuni.myhome.web.rest.AgencyRestController.get*(..))")
    void agencyRestControllerGetMethods(){}

    @Pointcut("execution(* bg.softuni.myhome.web.AgencyProfileController.*(..))")
    void agencyProfileControllerGetMethods(){}

    @Pointcut("execution(* bg.softuni.myhome.web.OfferAddController.getAddOfferPageOne(..))")
    void offerAddPageOneMethod(){}


    @Before(value = "offerAddPageOneMethod() && args(userVisibleId, appUserDetails, model)",
            argNames = "userVisibleId,  appUserDetails, model")
    private static void authorizeAddOffer(String userVisibleId, AppUserDetails appUserDetails, Model model ) {
        allowOperation(userVisibleId, appUserDetails);
    }

    @Before(value = "agencyProfileControllerGetMethods() && args(userVisibleId, appUserDetails, model)",
            argNames = "userVisibleId,appUserDetails, model")
    private static void authorizeAgencyProfile(String userVisibleId, AppUserDetails appUserDetails, Model model) {
        allowOperation(userVisibleId, appUserDetails, model);
    }


    @Before(value = "agencyRestControllerGetMethods() && args(userVisibleId, appUserDetails)",
            argNames = "userVisibleId,appUserDetails")
    private static void authorizeRequestsOffers(String userVisibleId, AppUserDetails appUserDetails) {
        allowOperation(userVisibleId, appUserDetails);
    }


    @Before(value = "agencyRequestsControllerGetMethods() && args(userVisibleId, appUserDetails)",
            argNames = "userVisibleId,appUserDetails")
    private static void authorizeAgencyRequests(String userVisibleId, AppUserDetails appUserDetails) {
       allowOperation(userVisibleId, appUserDetails);
    }

    @Before(value = "allAgencyOffersControllerMethods() && args(userVisibleId, appUserDetails)",
            argNames = "userVisibleId,appUserDetails")
    private static void authorizeAgencyOffers(String userVisibleId, AppUserDetails appUserDetails) {
      allowOperation(userVisibleId, appUserDetails);
    }



    private static void allowOperation(String userVisibleId,
                                         @AuthenticationPrincipal AppUserDetails appUserDetails) {
        if (!appUserDetails.getVisibleId().equals(userVisibleId)) {
            throw new UserNotAuthorizedException("Not authorised for this page", userVisibleId);
        }

    }

    private static void allowOperation(String userVisibleId,
                                       @AuthenticationPrincipal AppUserDetails appUserDetails, Model model) {

        if (!appUserDetails.getVisibleId().equals(userVisibleId)) {
            throw new UserNotAuthorizedException("Not authorised for this page", userVisibleId);
        }

    }




}
