package com.freedom.chatmodule.security;

import com.freedom.chatmodule.security.providers.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * @Author kede·W  on  2023/4/12
 */

@Component
public class MyAuthenticationManager implements AuthenticationManager {
    @Autowired
    CustomAuthenticationProvider customAuthenticationProvider;


    /*其他登陆方式的 provider */
//    @Autowired
//    CustomAuthenticationProvider customAuthenticationProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication result = null;
        try {
            result = customAuthenticationProvider.authenticate(authentication);
        } catch (AuthenticationException e) {
            System.out.println("使用其他登陆方式进行验证");
//            result = anotherCustomAuthenticationProvider.authenticate(authentication);
        }
        return result;
    }
}
