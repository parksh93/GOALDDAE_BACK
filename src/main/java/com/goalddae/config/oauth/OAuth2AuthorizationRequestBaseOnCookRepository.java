package com.goalddae.config.oauth;

import com.goalddae.util.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.web.util.WebUtils;

import java.io.IOException;

public class OAuth2AuthorizationRequestBaseOnCookRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {
    public final static String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    private final static int COOKIE_EXPIRE_SECOND = 18000;

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request){
        Cookie cookie = WebUtils.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        // 쿠키에 대한 정보를 역직렬화해 자바 내부에서 쓸 수 있게 만들어 리턴
        return CookieUtil.deserialize(cookie, OAuth2AuthorizationRequest.class);

    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        // 저장하려고 했지만 요청정보가 null인 경우(비정상적인 인증요청인 경우)
        if(authorizationRequest == null){
            removeAuthorizationRequest(request, response); // 인증정보 제거
            return;
        }
        // 쿠키에 직렬화된 토큰 저장
            CookieUtil.addCookie(response,
            OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
            CookieUtil.serialize(authorizationRequest),
            COOKIE_EXPIRE_SECOND);

    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        return this.loadAuthorizationRequest(request);
    }

    // 실질적인 삭제는 쿠키 삭제로 이뤄짐
    public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response){
        CookieUtil.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
    }
}
