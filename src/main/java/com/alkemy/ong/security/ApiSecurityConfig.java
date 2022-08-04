package com.alkemy.ong.security;

import com.alkemy.ong.Utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_URL = "/auth/login";
    private static final String REGISTER_URL = "/auth/register";
//  private static final String SWAGGER_URL = "/**";                   //Descomententar esta linea para usar swagger
    private final CustomExceptionHandler customExceptionHandler;
    private final CustomAuthorizationFilter customAuthorizationFilter;
    private final JwtUtil jwtUtil;

    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBeam(authenticationConfiguration), jwtUtil);
        customAuthenticationFilter.setFilterProcessesUrl(LOGIN_URL);
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(customExceptionHandler, LogoutFilter.class);
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore( customAuthorizationFilter, CustomAuthenticationFilter.class );
        http.authorizeRequests().antMatchers(LOGIN_URL,REGISTER_URL).permitAll();
//        http.authorizeRequests().antMatchers(SWAGGER_URL).permitAll(); //Descomententar esta linea para usar swagger
//        http.authorizeRequests().antMatchers("/organization/public/**").hasRole("ADMIN");
        http.authorizeRequests().anyRequest().hasAnyRole( "USER", "ADMIN" );
    }
    @Bean
    public AuthenticationManager authenticationManagerBeam(AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }

}
