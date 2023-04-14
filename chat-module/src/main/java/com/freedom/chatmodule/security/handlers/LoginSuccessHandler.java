package com.freedom.chatmodule.security.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freedom.chatmodule.common.resultmsg.SuccessResponse;
import com.freedom.chatmodule.security.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.session.SessionManagementFilter;
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
@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private  final static String RESPONSE_TYPE= "application/json;charset=utf-8";
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {


        String tokenResponse = jwtTokenProvider.generateToken(authentication);
        log.info("respnse 返回的 Token == : {}", tokenResponse);
        response.setHeader(AUTHORIZATION_HEADER, TOKEN_PREFIX + tokenResponse);
        response.setContentType(RESPONSE_TYPE);
        PrintWriter writer = response.getWriter();
        SuccessResponse<String> msg = SuccessResponse.success("自定义登录成功");
        writer.write(new ObjectMapper().writeValueAsString(msg));
    }
}
