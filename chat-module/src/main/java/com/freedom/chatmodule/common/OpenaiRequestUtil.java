package com.freedom.chatmodule.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freedom.chatmodule.config.openai.OpenAiProperties;
import com.freedom.chatmodule.utils.AesCryptoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Author kede·W  on  2023/3/20
 */
/*
通常情况下，我们会把工具类定义为静态方法或者单例模式，这样就可以在应用中任何地方直接调用这些方法，而无需进行实例化或者注入操作。
*/

@Service
public class OpenaiRequestUtil {

    @Autowired
    OpenAiProperties openAiProperties;

    @Autowired
    ObjectMapper objectMapper;


    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    AesCryptoUtil aesCryptoUtil;

//    public OpenaiRequestUtil(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }

    public  String sendGetRequest(String url, HttpHeaders headers) {
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        return response.getBody();
    }

    public  String sendPostRequest(String url, HttpHeaders headers, Object body) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        return response.getBody();
    }

    public <T> T get(String url, Class<T> responseType,Object body) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
//        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
//        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
        T forObject = restTemplate.getForObject(url, responseType);
        return forObject;
    }

    public <T> T get(String url, Class<T> responseType) {
        T forObject = restTemplate.getForObject(url, responseType);
        return forObject;
    }

    public String get(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36 Edg/111.0.1661.54");
        String forObject = restTemplate.getForObject(url, String.class);
        return forObject;
    }



    public <T> T post(String url, Object requestBody, Class<T> responseType) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + openAiProperties.getApikey());
        headers.add("User-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                "Chrome/58.0.3029.110 Safari/537.3");
        String string = objectMapper.writeValueAsString(requestBody);
        HttpEntity<Object> entity = new HttpEntity<>(string, headers);
        System.out.println(restTemplate);
        System.out.println("entity:");
        System.out.println(entity);

        ResponseEntity<T> response = restTemplate.exchange(
                "https://api.openai.com/v1/chat/completions",
                HttpMethod.POST, entity, responseType);
        return response.getBody();
    }

    public <T> T post(String url,String token, Object requestBody,
                      Class<T> responseType) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        token = aesCryptoUtil.decrypt(token);
        System.out.println("几米后");
        System.out.println(token);
        token = token.equals("") ? openAiProperties.getApikey() : token;
        headers.add("Authorization", "Bearer " + token);
        headers.add("User-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                "Chrome/58.0.3029.110 Safari/537.3");
        String string = objectMapper.writeValueAsString(requestBody);
        HttpEntity<Object> entity = new HttpEntity<>(string, headers);
        System.out.println(restTemplate);
        System.out.println("entity:");
        System.out.println(entity);

        ResponseEntity<T> response = restTemplate.exchange(
                "https://api.openai.com/v1/chat/completions",
                HttpMethod.POST, entity, responseType);
        return response.getBody();
    }

}

