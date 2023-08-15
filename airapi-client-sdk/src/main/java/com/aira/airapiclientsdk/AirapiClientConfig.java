package com.aira.airapiclientsdk;

import com.aira.airapiclientsdk.client.AirapiClient;
import jdk.nashorn.internal.objects.annotations.Property;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("airapi.client")
@Data
@ComponentScan
public class AirapiClientConfig {
    private String accessKey;
    private String secretKey;

    @Bean
    public AirapiClient airapiClient(){
        return new AirapiClient(accessKey, secretKey);
    }
}
