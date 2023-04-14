package com.freedom.chatmodule.security;

import com.freedom.chatmodule.security.filters.CustomAuthenticationFilter;
import com.freedom.chatmodule.security.handlers.LoginFailedHandler;
import com.freedom.chatmodule.security.handlers.LoginSuccessHandler;
import com.freedom.chatmodule.security.handlers.MyAccessDeniedHandler;
import com.freedom.chatmodule.security.handlers.MyAuthenticationEntryPoint;
import com.freedom.chatmodule.security.jwt.JwtAuthenticationFilter;
import com.freedom.chatmodule.security.providers.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @Author kede·W  on  2023/4/12
 */
@Configuration
@EnableWebSecurity
public class MySecurityConfig {
    @Autowired
    CustomAuthenticationFilter customAuthenticationFilter;

    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.cors().and().csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 禁用Session
                .and()
                .authorizeRequests()
                .antMatchers("/api/login").permitAll()
                .anyRequest().authenticated()

                .and()
                .addFilterBefore(customAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling()
                .accessDeniedHandler(new MyAccessDeniedHandler())
                .authenticationEntryPoint(new MyAuthenticationEntryPoint());
//

//                .formLogin(
//                        form -> form
//                                .loginPage("/login")
//                                .loginProcessingUrl("/login")
//                                .defaultSuccessUrl("/users")
//                                .permitAll()
//                ).logout(
//                logout -> logout
//                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                        .permitAll()

//        );
        return http.build();
    }


}
