package com.goalddae.config;

import com.goalddae.config.jwt.TokenProvider;
import jakarta.servlet.DispatcherType;
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
@Configuration
public class BasicSecurityConfig {
    private final UserDetailsService userService;
    private final TokenProvider tokenProvider;

    @Autowired
    public BasicSecurityConfig(UserDetailsService userService, TokenProvider tokenProvider){
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public WebSecurityCustomizer configure(){
        return web -> web.ignoring()
                .requestMatchers("/static/**")
                .dispatcherTypeMatchers(DispatcherType.FORWARD);

    }

    // http 요청에 대한 웹 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authenticationConfig -> {
                    authenticationConfig.requestMatchers( "/admin", "/admin/**").hasRole("admin")
                            .requestMatchers("/manager", "manager/**" ).hasAnyRole("manager", "admin")
                            .requestMatchers("/user/myInfo").hasRole("user")
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
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailService) throws Exception{
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder);
        return builder.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter(){
        return new TokenAuthenticationFilter(tokenProvider);
    }
}
