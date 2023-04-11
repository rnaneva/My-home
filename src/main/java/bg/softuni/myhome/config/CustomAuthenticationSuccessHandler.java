package bg.softuni.myhome.config;

import bg.softuni.myhome.exception.UserNotAuthorizedException;
import bg.softuni.myhome.model.AppUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Set;

import static bg.softuni.myhome.commons.StaticVariables.ROLE_ADMIN;
import static bg.softuni.myhome.commons.StaticVariables.ROLE_MODERATOR;

@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws  IOException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if(roles.contains(ROLE_ADMIN)){
            try {
                response.sendRedirect("/admin");
            } catch (IOException e){
                throw new UserNotAuthorizedException("onAuthenticationSuccess", ROLE_ADMIN);
            }

        } else if (roles.contains(ROLE_MODERATOR)){
            AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();
            try {
                response.sendRedirect("/agency/" + appUserDetails.getVisibleId());
            } catch (IOException e){
                throw new UserNotAuthorizedException("onAuthenticationSuccess", ROLE_MODERATOR);
            }
        } else {
            response.sendRedirect("/");
        }

    }
}
