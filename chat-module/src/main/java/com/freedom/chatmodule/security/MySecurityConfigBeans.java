package com.freedom.chatmodule.security;

import com.freedom.chatmodule.security.filters.CustomAuthenticationFilter;
import com.freedom.chatmodule.security.handlers.LoginFailedHandler;
import com.freedom.chatmodule.security.handlers.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author kede·W  on  2023/4/14
 */
@Configuration
public class MySecurityConfigBeans {
    @Autowired
    LoginSuccessHandler loginSuccessHandler;
    @Autowired
    LoginFailedHandler loginFailedHandler;

    @Autowired
    MyAuthenticationManager myAuthenticationManager;

    /*将过滤器 认证器provider 注册到这里*/
    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter(){
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter();
        customAuthenticationFilter.setAuthenticationManager(myAuthenticationManager);
        customAuthenticationFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        customAuthenticationFilter.setAuthenticationFailureHandler(loginFailedHandler);
        return customAuthenticationFilter;
    }
}
