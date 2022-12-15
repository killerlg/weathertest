package com.toprate.test.weather.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    @Value("${weather.host}")
    private String host;

    @Bean
    WebClient getWebClientBean(){
        return WebClient.create(this.host);
    }
}
