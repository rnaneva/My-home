package bg.softuni.myhome.interceptor;

import bg.softuni.myhome.service.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;


public class AuthorizationInterceptor implements HandlerInterceptor {

    private final static String VIOLATION_ATTEMPT = "AUTHORIZATION VIOLATION ATTEMPT";
    private final static Logger LOGGER = LoggerFactory.getLogger(AuthorizationInterceptor.class);
    private final EmailService emailService;


    public AuthorizationInterceptor(EmailService emailService) {
        this.emailService = emailService;
    }



    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler, Exception exception) {

        String remoteUser = request.getRemoteUser();
        String requestURI = request.getRequestURI();


        if (response.getStatus() == 401) {
            LOGGER.warn("{}: {} - {}", VIOLATION_ATTEMPT, requestURI, remoteUser);
            emailService.sendEmailToSecurityDept(remoteUser, requestURI);
        }


    }


}
