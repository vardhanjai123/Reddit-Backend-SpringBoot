package com.jaivardhan.springbootredditclone.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtProvider {

    private final String symmetricKey;

    @Autowired
    public JwtProvider(@Value("${symmetricKey}") String symmetricKey) {
        this.symmetricKey = symmetricKey;
    }
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(symmetricKey).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, symmetricKey).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

//
//    public String generateToken(User user)
//    {
//        System.out.println("*******************"+symmetricKey);
//        Map<String,Object> claims=new HashMap<>();
//        System.out.println(user.getUsername());
//        String ans= Jwts.builder()
//                .setSubject(user.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10))
//                .signWith(SignatureAlgorithm.HS256,symmetricKey.getBytes())
//                .compact();
//        System.out.println(getUserNameFromJwt(ans));
//        return ans;
//    }

//    public boolean validateToken(String jwt)
//    {
//        Jwts.parser().setSigningKey(symmetricKey).parseClaimsJws(jwt);
//        return true;
//    }
//
//    public String getUserNameFromJwt(String jwt)
//    {
//
//        return Jwts.parser().setSigningKey(symmetricKey.getBytes()).parseClaimsJws(jwt).getBody().getSubject();
//    }


}
