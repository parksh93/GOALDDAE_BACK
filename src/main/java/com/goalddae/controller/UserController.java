package com.goalddae.controller;

import com.goalddae.dto.user.GetUserInfoDTO;
import com.goalddae.dto.user.LoginDTO;
import com.goalddae.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public List<Boolean> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        String token = userService.generateTokenFromLogin(loginDTO);
        if(!token.equals("")){
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setSecure(true);

            response.addCookie(cookie);
            return List.of(true);
        }else{
            return List.of(false);
        }
    }

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    public List<?> getUserInfo(@CookieValue(required = false) String token){
        if(token == null){
            return List.of("null");
        }
        GetUserInfoDTO userInfoDTO = userService.getUserInfo(token);

        return List.of(userInfoDTO);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public List<Boolean> logout(HttpServletResponse response){
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return List.of(true);
    }
}
