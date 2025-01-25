package ru.skypro.homework.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
//import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Ads API Documentation",
                version = "1.0",
                description = "API documentation for Ads project",
                contact = @Contact(name = "SkyPro", url = "https://sky.pro", email = "support@sky.pro")
        )
)
public class SwaggerConfig {

//    @Bean
//    public GroupedOpenApi customImplementation() {
//        return GroupedOpenApi.builder()
//                .group("ads")
//                .addOpenApiCustomiser(openApi -> openApi.info(new Info().title("Ads API").version("1.0")))
//                .build();
//    }

//    @Bean
//    public GroupedOpenApi publicApi() {
//        return GroupedOpenApi.builder()
//                .group("public")
//                .pathsToMatch("/public/*")
//                .build();
//    }

}
