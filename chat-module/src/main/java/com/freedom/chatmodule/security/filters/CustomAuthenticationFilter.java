package com.freedom.chatmodule.security.filters;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.freedom.chatmodule.security.tokens.CustomAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @Author kede·W  on  2023/4/12
 */
@Slf4j
public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    /**
     * 前端传来的 参数名 - 用于request.getParameter 获取
     */
    private final String PARAMS_USERNAME_KEY ="username";

    private final String PARAMS_PASSWORD_KEY ="password";
    //从前台传过来的参数
    private String usernameParameter = PARAMS_USERNAME_KEY;

    private String passwordParameter = PARAMS_PASSWORD_KEY;
    /**
     * 是否 仅仅post方式
     */
    private boolean postOnly = true;

    public CustomAuthenticationFilter(){
        super(new AntPathRequestMatcher("/api/login","POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if(postOnly && !request.getMethod().equals("POST") ){
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }else{
            JSONObject jsonObject = obtainPostUsernamePassword(request);
            String username = jsonObject.getString(this.PARAMS_USERNAME_KEY);
            username = (username != null) ? username : "";
            username = username.trim();
            String password = jsonObject.getString(this.PARAMS_PASSWORD_KEY);
            password = (password != null) ? password : "";
            CustomAuthenticationToken customAuthenticationToken =
                    new CustomAuthenticationToken(username, password);
            setDetails(request, customAuthenticationToken);
            log.info("用户名 ：{}===============",username);

            Authentication authenticate = this.getAuthenticationManager().authenticate(customAuthenticationToken);

            return authenticate;
        }
    }

    /**
     * 获取 头部信息 让合适的provider 来验证他
     */
    protected void setDetails(HttpServletRequest request,
                              CustomAuthenticationToken token) {
        token.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    @Nullable
    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(this.passwordParameter);
    }

    @Nullable
    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(this.usernameParameter);
    }

    @Nullable
    protected JSONObject obtainPostUsernamePassword(HttpServletRequest request) throws IOException {
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            // 逐行读取请求体并使用 StringBuilder 进行拼接
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
            // 解析 JSON 数据并返回用户名
            JSONObject jsonObject = JSON.parseObject(requestBody.toString());

            return jsonObject;
        } finally {
            // 关闭 BufferedReader 以释放资源
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // 忽略异常
                }
            }
        }
    }

}
