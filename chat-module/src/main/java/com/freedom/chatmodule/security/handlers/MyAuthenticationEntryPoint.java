package com.freedom.chatmodule.security.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freedom.chatmodule.common.resultmsg.SuccessResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author kede·W  on  2023/4/14
 */
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private  final static String RESPONSE_TYPE= "application/json;charset=utf-8";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        StringBuffer requestURL = request.getRequestURL();
        System.out.println(requestURL);
        response.setContentType(RESPONSE_TYPE);
        PrintWriter writer = response.getWriter();
        SuccessResponse<String> msg = SuccessResponse.denied("你好像忘了登录");
        writer.write(new ObjectMapper().writeValueAsString(msg));
    }
}
