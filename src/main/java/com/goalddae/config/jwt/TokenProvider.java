package com.goalddae.config.jwt;

import com.goalddae.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TokenProvider {
    private final JwtProperties jwtProperties;

    public String generateToken(User user, Duration expiredAt){
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    public String generateLoinIdToken(String loginId, Duration expiredAt) {
        Date now = new Date();
        return makeLoginIdToken(new Date(now.getTime() + expiredAt.toMillis()), loginId);
    }

    // 내부적으로 토큰 생성 *
    private String makeToken(Date expiry, User user){
        Date now = new Date();

        String auth = "";

        if(user.getAuthority().equals("user")){
                auth = "ROLE_USER";
        }else if(user.getAuthority().equals("manager")){
                auth = "ROLE_MANAGER";
        }else if(user.getAuthority().equals("admin")){
                auth = "ROLE_ADMIN";
        }
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .setSubject(user.getEmail())
                .claim("id", user.getId())
                .claim("auth", auth)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    private String makeLoginIdToken(Date expiry, String loginId){
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .setSubject(loginId)
                .claim("loginId", loginId)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    // 토큰의 유효성 검증 *
    // 발급된 토큰을 입력받았을때, 유효하면 true, 아니면 false 리턴
    public boolean validToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    // 토큰 기반으로 인증 정보를 리턴
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        String auth = claims.get("auth", String.class);

        Set<SimpleGrantedAuthority> authorities =
                Collections.singleton(new SimpleGrantedAuthority(auth));

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User(claims.getSubject(),
                        "",authorities
                ),
                token, authorities
        );
        return authentication;
    }

    // 토큰 기반으로 유저번호를 가져오게 하는 메서드
    public Long getUserId(String token){
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }

    public String getLoginId(String token){
        Claims claims = getClaims(token);
        return claims.get("loginId", String.class);
    }

    public Date getDuration(String token){
        Claims claims = getClaims(token);
        return claims.getExpiration();
    }

    // 토큰 입력시 클레임을 리턴하도록 해 주는 메서드
    private Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }
}
