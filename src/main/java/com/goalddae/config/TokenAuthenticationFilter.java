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

    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(2);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofSeconds(10);
    public static final String ACCESS_TOKEN_COOKIE_NAME = "token";
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie [] cookies = request.getCookies();
        String token = "";
        String refreshToken = "";

        if(cookies != null){
            for (Cookie cookie: cookies) {
                if(cookie.getName().equals("token")){
                    token = cookie.getValue();
                }
                if(cookie.getName().equals("refreshToken")){
                    refreshToken = cookie.getValue();
                }
            }
        }

        if(!token.equals("")) {
            if (tokenProvider.validToken(token)) {
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                if(!refreshToken.equals("")){
                    Long userId = tokenProvider.getUserId(refreshToken);
                    RefreshToken refreshTokenEntity = refreshTokenService.findByUserId(userId);
                    boolean validRefreshToken = tokenProvider.validToken(refreshTokenEntity.getRefreshToken());

                    if (validRefreshToken && refreshToken.equals(refreshTokenEntity.getRefreshToken())) {
                        User user = userJPARepository.findById(userId).get();
                        String newAccessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
                        String newRefreshToken = tokenProvider.generateToken(user, REFRESH_TOKEN_DURATION);

                        refreshTokenService.saveRefreshToken(userId, newRefreshToken);
                        CookieUtil.addCookie(response, ACCESS_TOKEN_COOKIE_NAME, newAccessToken);
                        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, newRefreshToken);

                        Authentication authentication = tokenProvider.getAuthentication(newAccessToken);
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
