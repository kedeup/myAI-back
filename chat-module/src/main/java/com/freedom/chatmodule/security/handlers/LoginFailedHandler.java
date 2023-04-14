package com.freedom.chatmodule.security.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freedom.chatmodule.common.resultmsg.SuccessResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author kede·W  on  2023/4/14
 */
@Component
public class LoginFailedHandler implements AuthenticationFailureHandler {
    private  final static String RESPONSE_TYPE= "application/json;charset=utf-8";
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType(RESPONSE_TYPE);
        PrintWriter writer = response.getWriter();
        SuccessResponse<String> msg = SuccessResponse.success("登录失败");
        writer.write(new ObjectMapper().writeValueAsString(msg));
    }
}
