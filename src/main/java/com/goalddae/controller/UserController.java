package com.goalddae.controller;

import com.goalddae.dto.post.UserPostsResponse;
import com.goalddae.dto.user.CheckLoginIdDTO;
import com.goalddae.dto.user.CheckNicknameDTO;
import com.goalddae.dto.user.GetUserInfoDTO;
import com.goalddae.dto.user.LoginDTO;
import com.goalddae.entity.CommunicationBoard;
import com.goalddae.entity.UsedTransactionBoard;
import com.goalddae.entity.User;
import com.goalddae.exception.NotFoundMatchException;
import com.goalddae.exception.NotFoundPostException;
import com.goalddae.service.UserServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<String> updateUserInfo(User user) {
        userService.update(user);
        return ResponseEntity.ok("수정되었습니다.");
    }

    @RequestMapping(value = "/posts/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> viewUserPosts(@PathVariable long userId) {

        List<CommunicationBoard> communicationBoardsList = userService.getUserCommunicationBoardPosts(userId);
        List<UsedTransactionBoard> usedTransactionBoardList = userService.getUserUsedTransactionBoardPosts(userId);

        List<Object> combinedList = new ArrayList<>();
        combinedList.addAll(communicationBoardsList);
        combinedList.addAll(usedTransactionBoardList);

        return ResponseEntity.ok(combinedList);
    }

    // 매치테이블 생성되면 추가해야할 코드
//    @GetMapping("/match/{userId}")
//    public ResponseEntity<List<Match>> getMatchById(@PathVariable long userId) {
//
//        try {
//            List<Match> matchList = userMatchService.findMatchById(userId);
//            return ResponseEntity.ok(matchList);
//
//        } catch (NotFoundMatchException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//
//    }

}
