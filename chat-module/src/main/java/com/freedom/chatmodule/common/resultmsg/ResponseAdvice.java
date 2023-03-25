package com.freedom.chatmodule.common.resultmsg;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * @Author kede·W  on  2023/3/21
 */
@RestControllerAdvice(basePackages = "com.freedom.chatmodule.controller")
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 判断是否需要对响应进行处理，这里默认全部支持处理
        return true;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 对响应进行处理，比如添加返回码、时间戳等
        if(Objects.isNull(body)){
            return SuccessResponse.success();
        }
        if(body instanceof String){
            return objectMapper.writeValueAsString(SuccessResponse.success(body));
        }

        return SuccessResponse.success(body);
    }
}
