package nelsonssoares.ecomshoppingapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class EcomShoppingApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcomShoppingApiApplication.class, args);
    }

}
