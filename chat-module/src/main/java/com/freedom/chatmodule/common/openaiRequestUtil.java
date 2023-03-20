package com.freedom.chatmodule.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @Author kede·W  on  2023/3/20
 */
/*
通常情况下，我们会把工具类定义为静态方法或者单例模式，这样就可以在应用中任何地方直接调用这些方法，而无需进行实例化或者注入操作。
*/
public class openaiRequestUtil {

    @Value("${openai.apikey}")
    private String appName;

    private static RestTemplate restTemplate = new RestTemplate();

    public static String sendGetRequest(String url, HttpHeaders headers) {
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        return response.getBody();
    }

    public static String sendPostRequest(String url, HttpHeaders headers, Object body) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        return response.getBody();
    }

}
