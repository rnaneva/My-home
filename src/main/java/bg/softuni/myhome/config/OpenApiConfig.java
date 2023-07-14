package bg.softuni.myhome.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openApi(){
        return new OpenAPI().info(
                new Info().title("My Home API")
                        .contact(
                                new Contact()
                                        .email("office@my-home.now").name("office")

                        )
                        .description("A web application designed to suggest properties for rent or sale offered by various real estate agencies. The agency account provides offers upload / edit and easy management of received requests.\")")
        );
    }
}
