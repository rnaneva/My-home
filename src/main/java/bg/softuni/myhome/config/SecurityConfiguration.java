package bg.softuni.myhome.config;

import bg.softuni.myhome.model.enums.UserRoleEnum;
import bg.softuni.myhome.repository.UserRepository;
import bg.softuni.myhome.service.AppUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
@EnableMethodSecurity()
public class SecurityConfiguration {

    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    public SecurityConfiguration(AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   SecurityContextRepository securityContextRepository) throws Exception {

        httpSecurity.authorizeHttpRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers("/", "/agencies/**", "/api/**",  "/offers/**",  "/users/**", "/#advanced-search-title",
                        "/search/**").permitAll()
                .requestMatchers("/agency/**").hasRole(UserRoleEnum.MODERATOR.name())
                .requestMatchers("/admin/**", "/api/**", "/swagger-ui/**").hasRole(UserRoleEnum.ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/users/login")
                .successHandler(authenticationSuccessHandler)
                .failureForwardUrl("/users/login-error")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/").invalidateHttpSession(true)
                .and()
                .securityContext()
                .securityContextRepository(securityContextRepository);


        return httpSecurity.build();
    }


    @Bean
    public SecurityContextRepository securityContextRepository(){
        return new DelegatingSecurityContextRepository(
                new HttpSessionSecurityContextRepository(),
                new RequestAttributeSecurityContextRepository()
        );
    }


    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return new AppUserDetailsService(userRepository);
    }


}