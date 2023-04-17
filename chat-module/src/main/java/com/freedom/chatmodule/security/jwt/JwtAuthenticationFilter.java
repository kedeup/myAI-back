package com.freedom.chatmodule.security.jwt;

import com.freedom.chatmodule.security.tokens.CustomAuthenticationToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @Author kede·W  on  2023/4/13
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            if (request.getRequestURI().startsWith("/wxfirstcheck")) {
                filterChain.doFilter(request, response);
                return;
            }

            try {
                String token = getTokenFromHeader(request.getHeader(AUTHORIZATION_HEADER));
                if(token != null && !token.startsWith("Bearer ") && jwtTokenProvider.validateToken(token)){
                    String userNameFromToken = jwtTokenProvider.getUserNameFromToken(token);
                    //TODO :UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(userNameFromToken);
                    //TODO 这里应该封装成一个统一的对象
                    CustomAuthenticationToken customAuthenticationToken =
                            new CustomAuthenticationToken(userDetails.getUsername(), null, new ArrayList<>());
                    /*所以，只要您的token验证机制足够安全，并且没有其他地方泄露或修改了用户信息，那么在这种情况下，即使不设置Authentication对象，也不会存在任何安全风险。但是，为了保证代码的健壮性和规范性，建议在每次身份验证成功后都将Authentication对象设置到SecurityContextHolder中。*/
                    SecurityContextHolder.getContext().setAuthentication(customAuthenticationToken);

                }else {
                    // 如果没有找到token或者token验证失败，返回401 Unauthorized错误响应
                    logger.info("没有找到token或者token验证失败，返回401 Unauthorized错误响应");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            } catch (Exception e) {
                logger.error("Error authenticating user", e);
            }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromHeader(String header) {
        if (header != null && !header.isEmpty() && header.startsWith(TOKEN_PREFIX)) {
            return header.replace(TOKEN_PREFIX, "");
        }
        return null;
    }
}
