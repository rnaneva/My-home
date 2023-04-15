package bg.softuni.myhome.config;

import bg.softuni.myhome.interceptor.AuthorizationInterceptor;
import bg.softuni.myhome.interceptor.LogTimeForRequestInterceptor;
import bg.softuni.myhome.service.EmailService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class InterceptorConfigurer implements WebMvcConfigurer {

    private final EmailService emailService;

    public InterceptorConfigurer(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogTimeForRequestInterceptor())
                .addPathPatterns("/agency/requests/**", "/agency/offers/**")
                .addPathPatterns("/offers/rent", "/offers/sale")
                .addPathPatterns("/search/**");
        registry.addInterceptor(new AuthorizationInterceptor(emailService))
                .addPathPatterns("/agency/**", "/admin/**");

    }



}
