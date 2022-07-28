package com.alkemy.ong.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    
    static String AUTHORITIES_KEY;

    public static String SECRET_KEY;
    static int EXPIRATION_TIME;

    public static Authentication getAuthentication(HttpServletRequest request) {
        // 从Header中拿到token
        String token = request.getHeader(AUTHORIZATION);

        if (token != null && !token.isEmpty()) {
            // 解析 Token
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token.substring( 7 )).getBody();
            // 获取用户名
            String user = claims.getSubject();
            log.info( user );
            // 获取权限（角色）
            List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get(AUTHORITIES_KEY));
            log.info( authorities.toString() );

            // 返回验证令牌
            if(user != null){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken( claims, null, authorities );
                authenticationToken.setDetails( claims.get("userDetails") );
                return authenticationToken;
            }
            return null;
        }
        return null;
    }

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
    public static String extractRole(UserDetails userDetails){
        return userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    public static String generateToken(UserDetails userDetails){
        Map<String,Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).
                setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
                .claim(AUTHORITIES_KEY,extractRole( userDetails ))
                .claim( "userDetails", userDetails )
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY).compact();
    }
}
