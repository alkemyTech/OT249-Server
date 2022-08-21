package com.alkemy.ong.utils;

import com.alkemy.ong.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@Slf4j

public class JwtUtil {
    private final String AUTHORITIES_KEY;
    private final String SECRET_KEY;
    private final int EXPIRATION_TIME;
    private final UserService userService;
    public JwtUtil(
            @Value("${jwt.authorities.key}") String AUTHORITIES_KEY,
            @Value("${jwt.secret}") String SECRET_KEY,
            @Value("${jwt.expiration}") int EXPIRATION_TIME,
            @Lazy UserService userService) {

        this.AUTHORITIES_KEY = AUTHORITIES_KEY;
        this.SECRET_KEY = SECRET_KEY;
        this.EXPIRATION_TIME = EXPIRATION_TIME;
        this.userService = userService;
    }


    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);

        if (token != null && !token.isEmpty()) {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token.substring( 7 )).getBody();
            String user = claims.getSubject();
            UserDetails userDetails = userService.loadUserByUsername(user);
            List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get(AUTHORITIES_KEY));
            if(user != null){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken( userDetails, null, authorities );
                authenticationToken.setDetails( userDetails );
                return authenticationToken;
            }
            return null;
        }
        return null;
    }

    public static String extractRole(UserDetails userDetails){
        return userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).
                setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
                .claim(AUTHORITIES_KEY,extractRole( userDetails ))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY).compact();
    }
}
