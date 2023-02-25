package com.seedon.SeedOnTanda.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "seedon.external.config.storage")
@Configuration
public class StorageProperties {

    private String baseUrl;
    private String filePath;
    private String headerName;
    private String headerValue;

}
