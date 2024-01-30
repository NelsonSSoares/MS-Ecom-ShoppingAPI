package nelsonssoares.ecomshoppingapi.commons.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customApi() {
        return new OpenAPI().info(new Info().title("Ecommerce - Api de Pedidos do Ecommerce").version("1.0.0")
                .license(new License().name("MIT").url("https://github.com/NelsonSSoares/MS-Ecom-ShoppingApi"))
        );
    }
}
