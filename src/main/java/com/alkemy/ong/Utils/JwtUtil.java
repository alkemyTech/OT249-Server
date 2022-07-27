package com.alkemy.ong.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    
    private static String AUTHORITIES_KEY;

    private static String SECRET_KEY;
    private static int EXPIRATION_TIME;
    @Value("${jwt.secret}")
    public void setSECRET_KEY(String value) {

        SECRET_KEY = value;
    }
    @Value("${jwt.authorities.key}")
    public void setAUTHORITIES_KEY(String value) {

        AUTHORITIES_KEY = value;
    }
    @Value("${jwt.expiration}")
    public void setEXPIRATION_TIME(int value) {

        EXPIRATION_TIME = value;
    }

    public String extractUsername(String token){
        return extractClaims(token,Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaims(token,Claims::getExpiration);
    }

    public <T> T extractClaims(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public static String extractRole(UserDetails userDetails){
        return userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }
    public Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public static String generateToken(UserDetails userDetails){
        Map<String,Object> claims = new HashMap<>();
        return createToken(claims,userDetails.getUsername(),extractRole(userDetails));
    }

    private static String createToken(Map<String, Object> claims, String subject, String role){
        return Jwts.builder().setClaims(claims).
                setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .claim(AUTHORITIES_KEY,role)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
