package com.goalddae.config;

import com.goalddae.config.jwt.TokenProvider;
import com.goalddae.entity.RefreshToken;
import com.goalddae.entity.User;
import com.goalddae.exception.NotFoundTokenException;
import com.goalddae.exception.UnValidTokenException;
import com.goalddae.repository.RefreshTokenRepository;
import com.goalddae.repository.UserJPARepository;
import com.goalddae.service.RefreshTokenService;
import com.goalddae.service.UserService;
import com.goalddae.util.CookieUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final UserJPARepository userJPARepository;
    private final RefreshTokenService refreshTokenService;

    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static  String TOKEN_PREFIX = "Bearer ";
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(2);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofSeconds(10);
    public static final String ACCESS_TOKEN_COOKIE_NAME = "token";
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie [] cookies = request.getCookies();
        String token = "";
        String refreshToken = "";
        for (Cookie cookie: cookies) {
            if(cookie.getName().equals("token")){
                token = cookie.getValue();
            }
            if(cookie.getName().equals("refreshToken")){
                refreshToken = cookie.getValue();
            }

        }
        if(!token.equals("")) {
            if (tokenProvider.validToken(token)) {
                Authentication authentication = tokenProvider.getAuthentication(token);
                System.out.println(authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                System.out.println("==============" + refreshToken);
                if(!refreshToken.equals("")){
                    Long userId = tokenProvider.getUserId(refreshToken);
                    RefreshToken refreshTokenEntity = refreshTokenService.findByUserId(userId);
                    System.out.println("---------"+ refreshTokenEntity);
                    boolean validRefreshToken = tokenProvider.validToken(refreshTokenEntity.getRefreshToken());
                    if (validRefreshToken && refreshToken.equals(refreshTokenEntity.getRefreshToken())) {
                        System.out.println("*********토큰 재발급***********");
                        User user = userJPARepository.findById(userId).get();
                        String newAccessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
                        String newRefreshToken = tokenProvider.generateToken(user, REFRESH_TOKEN_DURATION);
                        refreshTokenService.saveRefreshToken(userId, newRefreshToken);
                        CookieUtil.addCookie(response, ACCESS_TOKEN_COOKIE_NAME, newAccessToken);
                        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, newRefreshToken);

                    }
//                    else {
//                        throw new UnValidTokenException("유효하지 않은 Refresh Token");
//                    }
                }
//                else {
//                    throw new NotFoundTokenException("Refresh Token 존재하지 않음");
//                }
            }
        }
//        else {
//            throw new NotFoundTokenException("토큰 미발급");
//        }

        filterChain.doFilter(request, response);
    }

    // Bearer 접두사 제거
    public String getAccessToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
