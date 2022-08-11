package com.alkemy.ong.security;

import com.alkemy.ong.utils.JwtUtil;
import com.alkemy.ong.service.UserService;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class CustomAuthorizationFilter extends GenericFilterBean {

    @Autowired
    UserService userService;
    @Autowired
    JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest r = (HttpServletRequest) request;
            Authentication authentication = jwtUtil.getAuthentication(r);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException | SignatureException e) {
            logger.error(e.getMessage());
        }
        filterChain.doFilter(request, response);

    }
}
