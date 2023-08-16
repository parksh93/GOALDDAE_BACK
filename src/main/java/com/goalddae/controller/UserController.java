package com.goalddae.controller;

import com.goalddae.dto.user.*;
import com.goalddae.entity.User;
import com.goalddae.service.UserServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService){
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Boolean> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        String token = userService.generateTokenFromLogin(loginDTO);
        if(!token.equals("")){
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setSecure(true);

            response.addCookie(cookie);
            return ResponseEntity.ok(true);
        }else{
            return ResponseEntity.badRequest().body(false);
        }
    }

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    public ResponseEntity<?> getUserInfo(@CookieValue(required = false) String token){
        if(token == null){
            return ResponseEntity.badRequest().body("");
        }
        GetUserInfoDTO userInfoDTO = userService.getUserInfo(token);

        return ResponseEntity.ok(userInfoDTO);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity<Boolean> logout(HttpServletResponse response){
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok(true);
    }

    @PostMapping("/checkLoginId")
    public List<Boolean> checkLoginId(@RequestBody CheckLoginIdDTO checkLoginIdDTO){
        return List.of(userService.checkLoginId(checkLoginIdDTO));
    }

    @RequestMapping(value = "/checkNickname", method = RequestMethod.POST)
    public List<Boolean> checkNickname(@RequestBody CheckNicknameDTO checkNicknameDTO){
        return List.of(userService.checkNickname(checkNicknameDTO));
    }

    @PostMapping("/signup")
    public void signup(@RequestBody User user){
        userService.save(user);
    }

    @RequestMapping(value = "/findLoginId", method = RequestMethod.POST)
    public ResponseEntity<ResponseFindLoginIdDTO> findLoginId(@RequestBody RequestFindLoginIdDTO requestFindLoginIdDTO){
        ResponseFindLoginIdDTO findLoginIdDTO = userService.getLoginIdByEmailAndName(requestFindLoginIdDTO);

        return ResponseEntity.ok(findLoginIdDTO);
    }
}
