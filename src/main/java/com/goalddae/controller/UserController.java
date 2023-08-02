package com.goalddae.controller;

import com.goalddae.config.jwt.TokenProvider;
import com.goalddae.dto.AccessTokenResponseDTO;
import com.goalddae.dto.user.LoginDTO;
import com.goalddae.entity.User;
import com.goalddae.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private TokenProvider tokenProvider;

    @Autowired
    public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder, TokenProvider tokenProvider){
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @RequestMapping("/login")
    public AccessTokenResponseDTO login(@RequestBody LoginDTO loginDTO){
        User userInfo = userService.getByCredentials(loginDTO.getLoginId());

        if (userInfo.getPassword().equals(loginDTO.getPassword())){
            String token = tokenProvider.generateToken(userInfo, Duration.ofHours(2));

            AccessTokenResponseDTO accessTokenResponseDTO = new AccessTokenResponseDTO(token);

            return accessTokenResponseDTO;
        }else{
            AccessTokenResponseDTO accessTokenResponseDTO = new AccessTokenResponseDTO("login fail");
            return accessTokenResponseDTO;
        }

    }
}
