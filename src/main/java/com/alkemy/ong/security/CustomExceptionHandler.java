package com.alkemy.ong.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;



@Log4j2
@Component
public class CustomExceptionHandler extends OncePerRequestFilter {

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {

        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("Spring Security Filter Message Exception: {} ", e.getMessage());
            log.debug("Spring Security Filter Chain Exception:", e );
            resolver.resolveException(request, response, null, e);
            try {
                response.setStatus( HttpServletResponse.SC_BAD_REQUEST );
                response.setContentType( HttpHeaderValues.APPLICATION_JSON.toString() );
                new ObjectMapper().writeValue(response.getOutputStream(), Map.of("errors" , e.getMessage()));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
