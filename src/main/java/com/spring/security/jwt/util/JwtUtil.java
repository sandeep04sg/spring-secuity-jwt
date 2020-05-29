package com.spring.security.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private final String SECRET_KEY="secret";

    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public Date extractexpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }

    public <T>T extractClaim(String token, Function<Claims,T> claimResolver){
        final Claims claims=extractAllclaims(token);
        return claimResolver.apply(claims);
    }
    public Claims extractAllclaims(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJwt(token).getBody();
    }

    /**
     * to generate token
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims=new HashMap<>();
        return createToken(claims,userDetails.getUsername());
    }

    /**
     * token expire
     * @param token
     * @return
     */
    public boolean isTokenExpire(String token){
        return extractexpiration(token).before(new Date());
    }

    /**
     * create token
     * @param claims
     * @param subject
     * @return
     */
    public String createToken(Map<String,Object> claims,String subject){
        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10))
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY).compact();
    }

    public Boolean validtoken(String token,UserDetails userDetails){
        final String username=extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpire(token));
    }
}
