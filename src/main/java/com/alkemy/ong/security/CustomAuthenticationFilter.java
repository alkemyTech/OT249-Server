package com.alkemy.ong.security;

import com.alkemy.ong.dtos.LoginRequestDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManagerBean) {

        this.authenticationManager = authenticationManagerBean;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            byte[] inputStreamBytes = StreamUtils.copyToByteArray(request.getInputStream());
            ObjectMapper objectMapper = new ObjectMapper();
            LoginRequestDTO jsonRequest = objectMapper.readValue(inputStreamBytes, LoginRequestDTO.class);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
                    UsernamePasswordAuthenticationToken(jsonRequest.getEmail(), jsonRequest.getPassword());
            response.setContentType(APPLICATION_JSON_VALUE);
            return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (IOException e) {
            System.out.println( "e.getMessage() = " + e.getMessage() );
        }
        return null;
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {

        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        new ObjectMapper().writeValue(response.getOutputStream(), Map.of("ok", false));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {

        User user = (User) authResult.getPrincipal();
        new ObjectMapper().writeValue(response.getOutputStream(), Map.of("user", user));
    }

}
