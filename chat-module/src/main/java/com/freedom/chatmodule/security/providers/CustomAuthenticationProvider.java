package com.freedom.chatmodule.security.providers;

import com.freedom.chatmodule.security.SysUserServiceImpl;
import com.freedom.chatmodule.security.tokens.CustomAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @Author kede·W  on  2023/4/12
 */
@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService UserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }
        log.info("CustomAuthenticationToken authentication request: %{}",
                authentication);
        CustomAuthenticationToken token = (CustomAuthenticationToken) authentication;
        String username = token.getPrincipal().toString();
        String password = "";

        UserDetails userDetails = UserDetailsService.loadUserByUsername(username);
        if (userDetails == null){
            throw new InternalAuthenticationServiceException(
                    "UserDetailsService returned null, which is an interface contract violation");
        }
        /*有了SysUserServiceImpl implements UserDetailsService
        可以通过UserDetailsService.loadUserByUsername获取用户信息SysUser*/
        CustomAuthenticationToken grantedToken =
                new CustomAuthenticationToken(userDetails.getUsername(),
                        userDetails.getPassword(),
                        new ArrayList<>());
        return grantedToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        /*isAssignableFrom()方法是Java反射API中的一个方法，用于检查某个类对象是否与另一个类对象相同或者是其父类或者超级接口*/
        return CustomAuthenticationToken.class.isAssignableFrom(authentication);

    }
}
