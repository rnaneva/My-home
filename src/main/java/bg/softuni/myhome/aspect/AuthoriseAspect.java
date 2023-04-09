package bg.softuni.myhome.aspect;

import bg.softuni.myhome.model.AppUserDetails;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import javax.naming.NoPermissionException;

@Aspect
@Configuration
public class AuthoriseAspect {

    @Pointcut("execution(* bg.softuni.myhome.web.AgencyOffersController.*(..))")
    void allAgencyOffersControllerMethods(){}

    @Pointcut("execution(* bg.softuni.myhome.web.AgencyRequestsController.get*(..))")
    void agencyRequestsControllerGetMethods(){}

    @Pointcut("execution(* bg.softuni.myhome.web.rest.AgencyRestController.get*(..))")
    void agencyRestControllerGetMethods(){}




    @Around(value = "agencyRestControllerGetMethods() && args(userVisibleId, appUserDetails)",
            argNames = "pjp,userVisibleId,appUserDetails")
    private static Object authorizeRequestsOffers(ProceedingJoinPoint pjp, String userVisibleId, AppUserDetails appUserDetails)
            throws Throwable {
        return allowOperation(pjp, userVisibleId, appUserDetails);
    }


    @Around(value = "agencyRequestsControllerGetMethods() && args(userVisibleId, appUserDetails)",
            argNames = "pjp,userVisibleId,appUserDetails")
    private static Object authorizeAgencyRequests(ProceedingJoinPoint pjp, String userVisibleId, AppUserDetails appUserDetails)
            throws Throwable {
        return allowOperation(pjp, userVisibleId, appUserDetails);
    }

    @Around(value = "allAgencyOffersControllerMethods() && args(userVisibleId, appUserDetails)",
            argNames = "pjp,userVisibleId,appUserDetails")
    private static Object authorizeAgencyPage(ProceedingJoinPoint pjp, String userVisibleId, AppUserDetails appUserDetails)
            throws Throwable {
        return allowOperation(pjp, userVisibleId, appUserDetails);
    }



    private static Object allowOperation(ProceedingJoinPoint pjp, String userVisibleId,
                                         @AuthenticationPrincipal AppUserDetails appUserDetails)
            throws Throwable {
        if (!appUserDetails.getVisibleId().equals(userVisibleId)) {
            throw new NoPermissionException();
        }
        return pjp.proceed();
    }



}
