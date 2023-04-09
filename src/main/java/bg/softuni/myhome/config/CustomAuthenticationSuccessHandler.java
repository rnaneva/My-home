package bg.softuni.myhome.config;

import bg.softuni.myhome.model.AppUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Set;

@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if(roles.contains("ROLE_ADMIN")){
            response.sendRedirect("/admin");
        } else if (roles.contains("ROLE_MODERATOR")){
            AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();
            response.sendRedirect("/agency/" + appUserDetails.getVisibleId());
        } else {
            response.sendRedirect("/");
        }

    }
}
