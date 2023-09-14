package com.goalddae.config;

import com.goalddae.config.jwt.TokenProvider;
import com.goalddae.config.oauth.OAuth2AuthorizationRequestBaseOnCookRepository;
import com.goalddae.config.oauth.OAuth2SuccessHandler;
import com.goalddae.config.oauth.OAuth2UserService;
import com.goalddae.repository.RefreshTokenRepository;
import com.goalddae.repository.UserJPARepository;
import com.goalddae.service.RefreshTokenService;
import com.goalddae.service.UserService;
import com.goalddae.util.CookieUtil;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final OAuth2UserService oAuth2UserService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserJPARepository userJPARepository;
    private final RefreshTokenService refreshTokenService;


    @Bean
    public WebSecurityCustomizer configure(){
        return web -> web.ignoring()
                .requestMatchers("/static/**")
                .dispatcherTypeMatchers(DispatcherType.FORWARD);

    }

    // 웹소켓을 사용하기 위한 cors 설정
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // http 요청에 대한 웹 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authenticationConfig -> {
                    authenticationConfig.requestMatchers( "/admin", "/admin/**").hasRole("ADMIN")
                            .requestMatchers("/manager", "manager/**" ).hasAnyRole("MANAGER", "ADMIN")
//                            .requestMatchers("/user/myInfo").hasRole("user")
                            .requestMatchers("/webSocket/**").permitAll()
//                            .requestMatchers("/user/myPage").hasAnyRole("user", "manager", "admin")
                            .anyRequest()
                            .permitAll();
                })
                .formLogin(AbstractHttpConfigurer::disable
                )
                .logout(logoutConfig -> {
                    logoutConfig
                            .logoutSuccessUrl("/")
                            .invalidateHttpSession(true);
                })
                .sessionManagement(sessionConfig -> {
                    sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .oauth2Login(oauth2Config ->{
                    oauth2Config.loginPage("/login") // 로그인 성공시
                            .authorizationEndpoint(endpointConfig -> endpointConfig
                                    .authorizationRequestRepository(oAuth2AuthorizationRequestBaseOnCookieRepository()))
                            .successHandler(oAuth2SuccessHandler())
                            .userInfoEndpoint(userInfoConfig -> userInfoConfig
                                    .userService(oAuth2UserService));
                })
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailService) throws Exception{
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailService)
                .passwordEncoder(bCryptPasswordEncoder);
        return builder.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter(){
        return new TokenAuthenticationFilter(tokenProvider, userJPARepository, refreshTokenService);
    }

    @Bean
    public OAuth2SuccessHandler oAuth2SuccessHandler() {
        return new OAuth2SuccessHandler(tokenProvider, refreshTokenRepository, oAuth2AuthorizationRequestBaseOnCookieRepository(),userService);
    }

    @Bean
    public OAuth2AuthorizationRequestBaseOnCookRepository oAuth2AuthorizationRequestBaseOnCookieRepository() {
        return new OAuth2AuthorizationRequestBaseOnCookRepository();
    }
}
