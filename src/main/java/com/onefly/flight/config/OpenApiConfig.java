package com.onefly.flight.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ONEFLY Flight API")
                        .version("V2.3")
                        .description("ONEFLY Flight API Specifications - Search, Verify, Order, Pay & Ticket, "
                                + "Order Details, Route Map, Bag Info, and Seat Selection APIs")
                        .contact(new Contact()
                                .name("ONEFLY API Support")
                                .email("apisupport@onefly.com")));
    }
}
