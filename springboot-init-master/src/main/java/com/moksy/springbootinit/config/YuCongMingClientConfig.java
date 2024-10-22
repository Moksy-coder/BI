package com.moksy.springbootinit.config;

import com.yupi.yucongming.dev.client.YuCongMingClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YuCongMingClientConfig {

    @Value("${yuapi.client.access-key}")
    private String accessKey;

    @Value("${yuapi.client.secret-key}")
    private String secretKey;

    @Bean
    public YuCongMingClient yuCongMingClient() {
        return new YuCongMingClient(accessKey, secretKey);
    }
}
