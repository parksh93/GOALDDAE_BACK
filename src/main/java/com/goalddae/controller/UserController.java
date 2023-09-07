package com.goalddae.controller;

import com.goalddae.dto.user.*;
import com.goalddae.entity.CommunicationBoard;
import com.goalddae.entity.UsedTransactionBoard;
import com.goalddae.entity.User;
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

        System.out.println(checkNicknameDTO.toString());

        return List.of(userService.checkNickname(checkNicknameDTO));
    }

    @PostMapping("/signup")
    public void signup(@RequestBody User user){
        userService.save(user);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<String> updateUserInfo(@RequestBody GetUserInfoDTO getUserInfoDTO) {

        System.out.println(getUserInfoDTO.toString());

        userService.update(getUserInfoDTO);
        return ResponseEntity.ok("수정 되었습니다.");
    }

    @RequestMapping(value = "/update/teamId", method = RequestMethod.PATCH)
    public ResponseEntity<String> updateUserTeamId(@RequestBody GetUserInfoDTO getUserInfoDTO){
        System.out.println(getUserInfoDTO);
        try {
            userService.updateTeamId(getUserInfoDTO);

            return ResponseEntity.ok("팀 ID가 업데이트되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("팀 ID 업데이트 중 오류 발생: " + e.getMessage());
        }
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

    @RequestMapping(value = "/findLoginId", method = RequestMethod.POST)
    public ResponseEntity<ResponseFindLoginIdDTO> findLoginId(@RequestBody RequestFindLoginIdDTO requestFindLoginIdDTO){
        ResponseFindLoginIdDTO findLoginIdDTO = userService.getLoginIdByEmailAndName(requestFindLoginIdDTO);

        return ResponseEntity.ok(findLoginIdDTO);
    }

    @RequestMapping(value = "/findPassword", method = RequestMethod.POST)
    public List<String> findPassword(@RequestBody RequestFindPasswordDTO findPasswordDTO, HttpServletResponse response){
        String token = userService.checkLoginIdAndEmail(findPasswordDTO);
        if(!token.equals("")) {
            Cookie cookie = new Cookie("loginIdToken", token);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setSecure(true);

            response.addCookie(cookie);
        }
        return List.of(token);
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.PATCH)
    public List<Boolean> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO,@CookieValue(required = false) String loginIdToken, HttpServletResponse response) {
       changePasswordDTO.setLoginIdToken(loginIdToken);
       boolean changeCheck = userService.changePassword(changePasswordDTO);
        Cookie cookie = new Cookie("loginIdToken", null);

        cookie.setMaxAge(0);
        cookie.setPath("/");

        response.addCookie(cookie);

        return List.of(changeCheck);
    }
}
