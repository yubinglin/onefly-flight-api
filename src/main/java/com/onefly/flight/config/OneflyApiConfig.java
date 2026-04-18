package com.onefly.flight.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "onefly.api")
public class OneflyApiConfig {

    private String baseUrl;
    private String cid;
    private String aesKey;
}
