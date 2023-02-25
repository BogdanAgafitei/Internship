package com.seedon.SeedOnTanda;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition(info = @Info(title = "Tanda API", version = "1.0", description = "Tanda Information"))
public class SeedOnTandaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeedOnTandaApplication.class, args);
    }

}
