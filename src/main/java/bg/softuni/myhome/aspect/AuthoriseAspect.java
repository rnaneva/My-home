package bg.softuni.myhome.aspect;

import bg.softuni.myhome.exception.UserNotAuthorizedException;
import bg.softuni.myhome.model.AppUserDetails;

import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Aspect
@Configuration
public class AuthoriseAspect {

    @Pointcut("execution(* bg.softuni.myhome.web.AgencyOffersController.*(..))")
    void allAgencyOffersControllerMethods(){}

    @Pointcut("execution(* bg.softuni.myhome.web.AgencyRequestsController.get*(..))")
    void agencyRequestsControllerGetMethods(){}

    @Pointcut("execution(* bg.softuni.myhome.web.rest.AgencyRestController.get*(..))")
    void agencyRestControllerGetMethods(){}




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
    private static void authorizeAgencyPage(String userVisibleId, AppUserDetails appUserDetails) {
      allowOperation(userVisibleId, appUserDetails);
    }



    private static void allowOperation(String userVisibleId,
                                         @AuthenticationPrincipal AppUserDetails appUserDetails) {
        if (!appUserDetails.getVisibleId().equals(userVisibleId)) {
            throw new UserNotAuthorizedException("Allow page for", userVisibleId);
        }

    }



}
