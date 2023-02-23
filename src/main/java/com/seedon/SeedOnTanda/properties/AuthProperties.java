package com.seedon.SeedOnTanda.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix = "seedon.external.config.auth")
public class AuthProperties {
    private String baseUrl;
    private String headerName;
    private String headerValue;
    private String authValidate;
}
