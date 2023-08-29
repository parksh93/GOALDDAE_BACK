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
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(2);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofHours(2);
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

        saveRefreshToken(user.getId(), refreshToken); // 토큰을 디비에 저장
//        addRefreshTokenToCookie(request, response, refreshToken);   // 사용자측에 보내기 위한 쿠키 적재

        // 전달된 리프레시토큰을 이용해 억세스토큰 생성 및 활용
        String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
        String targetUrl = getTargetUrl(accessToken);

        Cookie cookie = new Cookie("token", accessToken);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

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

//    private void addRefreshTokenToCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken){
//        int cookieMaxAge = (int) REFRESH_TOKEN_DURATION.toSeconds();
//        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);
//        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken, cookieMaxAge);
//    }

    // 액세스토큰을 소셜로그인 경로에 파라미터로 추가
    private String getTargetUrl(String token){
        return UriComponentsBuilder.fromUriString(REDIRECT_PATH)
                .queryParam("token", token)
                .build()
                .toUriString();
    }

    // 전달 완료 후 인증 관련 설정값, 쿠키 제거
    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response){
        // 상속 전 부모 클래스쪽에 정의된 메서드는 request에 대해서만 소거가 처리되므로, 쿠키 제거를 위해 reponse 부분도 추가 처리
        // 메서드 명칭은 부모쪽 메서드와 같지만 파라미터 종류가 다르므로 오버로딩
        super.clearAuthenticationAttributes(request);
        oAuth2AuthorizationRequestBaseOnCookRepository.removeAuthorizationRequestCookies(request,response);
    }
}
