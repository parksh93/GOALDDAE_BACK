package com.goalddae.controller;

import com.goalddae.dto.user.*;
import com.goalddae.entity.User;
import com.goalddae.service.UserServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.Path;

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
        boolean checkUser = userService.generateTokenFromLogin(loginDTO, response);

        if(checkUser){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.badRequest().body(false);
    }

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    public ResponseEntity<?> getUserInfo(@CookieValue(required = false) String token){
        GetUserInfoDTO userInfoDTO = userService.getUserInfo(token);

        return ResponseEntity.ok(userInfoDTO);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity<Boolean> logout(HttpServletResponse response){
        Cookie cookieToken = new Cookie("token", null);
        cookieToken.setMaxAge(0);
        cookieToken.setPath("/");

        Cookie cookieRefreshToken = new Cookie("refreshToken", null);
        cookieRefreshToken.setMaxAge(0);
        cookieRefreshToken.setPath("/");

        response.addCookie(cookieToken);
        response.addCookie(cookieRefreshToken);

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

    @PutMapping("/signup")
    public void signup(@RequestBody User user){
        userService.save(user);
    }

    @PatchMapping("/socialSignup")
    public void socialSignup(@RequestBody GetUserInfoDTO getUserInfoDTO){
        userService.updateSocialSignup(getUserInfoDTO);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<String> updateUserInfo(@RequestBody GetUserInfoDTO getUserInfoDTO) {
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

    @RequestMapping(value = "/findLoginId", method = RequestMethod.POST)
    public ResponseEntity<ResponseFindLoginIdDTO> findLoginId(@RequestBody RequestFindLoginIdDTO requestFindLoginIdDTO){
        ResponseFindLoginIdDTO findLoginIdDTO = userService.getLoginIdByEmailAndName(requestFindLoginIdDTO);

        return ResponseEntity.ok(findLoginIdDTO);
    }

    @RequestMapping(value = "/findPassword", method = RequestMethod.POST)
    public List<Boolean> findPassword(@RequestBody RequestFindPasswordDTO findPasswordDTO, HttpServletResponse response){
        boolean checkUser = userService.checkLoginIdAndEmail(findPasswordDTO, response);

        return List.of(checkUser);
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

    @RequestMapping(value = "/deleteAccount/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> deleteAccount(@PathVariable long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("탈퇴 되었습니다.");
    }

}
