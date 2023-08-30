package com.goalddae.config.oauth;

import com.goalddae.config.jwt.TokenProvider;
import com.goalddae.entity.RefreshToken;
import com.goalddae.entity.User;
import com.goalddae.repository.RefreshTokenRepository;
import com.goalddae.service.UserService;
import com.goalddae.util.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    public static final String ACCESS_TOKEN_COOKIE_NAME = "token";
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofSeconds(60);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofSeconds(30);
    public static final String REDIRECT_PATH = "http://localhost:3000";

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final OAuth2AuthorizationRequestBaseOnCookRepository oAuth2AuthorizationRequestBaseOnCookRepository;
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        System.out.println(authentication.getPrincipal());
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        User user = userService.findByEmail((String)oAuth2User.getAttributes().get("email"));

        String refreshToken = tokenProvider.generateToken(user, REFRESH_TOKEN_DURATION);

        saveRefreshToken(user.getId(), refreshToken);

        String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
        CookieUtil.addCookie(response, ACCESS_TOKEN_COOKIE_NAME, accessToken);

        // 인증 관련 설정값, 쿠키 제거
        clearAuthenticationAttributes(request, response);

        getRedirectStrategy().sendRedirect(request, response, REDIRECT_PATH);
    }

    private void saveRefreshToken(Long userId, String newRefreshToken){
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId);

        if(refreshToken != null){
            refreshToken.update(newRefreshToken);
        }else{
            refreshToken = new RefreshToken(userId, newRefreshToken);
        }

        refreshTokenRepository.save(refreshToken);
    }

    // 전달 완료 후 인증 관련 설정값, 쿠키 제거
    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response){
        super.clearAuthenticationAttributes(request);
        oAuth2AuthorizationRequestBaseOnCookRepository.removeAuthorizationRequestCookies(request,response);
    }
}
