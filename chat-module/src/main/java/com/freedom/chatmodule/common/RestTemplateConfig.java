package com.freedom.chatmodule.common;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.Duration;


/**
 * @Author kede·W  on  2023/3/22
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplateClient() {
//        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        SimpleClientHttpRequestFactory httpRequestFactory = new SimpleClientHttpRequestFactory();
        httpRequestFactory.setProxy(new Proxy(Proxy.Type.HTTP,
                new InetSocketAddress("127.0.0.1",7890)));

        RestTemplate restTemplate = new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofMinutes(1))
                .setReadTimeout(Duration.ofMinutes(1)).build();
        restTemplate.setRequestFactory(httpRequestFactory);
        return restTemplate;
    }
}
