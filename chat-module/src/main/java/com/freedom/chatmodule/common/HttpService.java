package com.freedom.chatmodule.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freedom.chatmodule.config.openai.OpenAiProperties;
import com.freedom.chatmodule.vo.CompletionVO;
import okhttp3.*;
import okio.Buffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author kede·W  on  2023/3/22
 */

@Service
public class HttpService {
    @Autowired
    OkHttpClient okHttpClient;

    @Autowired
    OpenAiProperties openAiProperties;
    @Autowired
    private ObjectMapper objectMapper;

    public HttpService(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public  ResponseBody  post(String path, Object requestObject) throws IOException {
//        String json = gson.toJson(requestObject);
        String json = objectMapper.writeValueAsString(requestObject);
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(openAiProperties.getBaseUrl() + path)
                .addHeader("Authorization", "Bearer " + openAiProperties.getApikey())
                .post(body)
                .build();
        Buffer buffer = new Buffer();
        body.writeTo(buffer);
        String actualRequestBody = buffer.readUtf8();

        System.out.println("actualRequestBody:");
        System.out.println(actualRequestBody);
        System.out.println(request);
        System.out.println(openAiProperties.getBaseUrl() + path);

        Response response = okHttpClient.newCall(request).execute();
        System.out.println("response:");

        System.out.println(response);

        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }
        ResponseBody responseBody = response.body();
        System.out.println("responseBody:");
        System.out.println(responseBody.string());
        CompletionVO completionVO = objectMapper.readValue(responseBody.string(), CompletionVO.class);
        System.out.println("completionVO");
        System.out.println(completionVO);
        return  responseBody;
//        return gson.fromJson(responseBody, responseClass);
    }

//    public String request(String url, Map<String, String> params, Map<String, String> headers, HttpMethod method) throws IOException {
//        Request request = buildRequest(url, params, headers, method);
//        Response response = okHttpClient.newCall(request).execute();
//        if (response.isSuccessful()) {
//            return response.body().string();
//        } else {
//            throw new IOException("Unexpected code " + response);
//        }
//    }
//
//    private Request buildRequest(String url, Map<String, String> params, Map<String, String> headers, HttpMethod method) {
//        Request.Builder builder = new Request.Builder();
//        switch (method) {
//            case GET:
//                url = buildUrlWithQueryString(url, params);
//                builder = builder.get();
//                break;
//            case POST:
//                RequestBody requestBody = buildRequestBody(params);
//                builder = builder.post(requestBody);
//                break;
//            default:
//                throw new IllegalArgumentException("Unsupported http method: " + method.name());
//        }
//        for (Map.Entry<String, String> entry : headers.entrySet()) {
//            builder.addHeader(entry.getKey(), entry.getValue());
//        }
//        builder.url(url);
//        return builder.build();
//    }




}
