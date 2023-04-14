package com.freedom.chatmodule.security.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freedom.chatmodule.common.resultmsg.SuccessResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author kede·W  on  2023/4/14
 */
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    private  final static String RESPONSE_TYPE= "application/json;charset=utf-8";

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType(RESPONSE_TYPE);
        PrintWriter writer = response.getWriter();
        SuccessResponse<String> msg = SuccessResponse.denied("你好像没有权限");
        writer.write(new ObjectMapper().writeValueAsString(msg));
    }
}
