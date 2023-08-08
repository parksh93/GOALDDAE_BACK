package com.goalddae.controller;

import com.goalddae.dto.user.GetUserInfoDTO;
import com.goalddae.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mypage")
public class UserInfoController {

    private final UserInfoService userInfoService;
    @Autowired
    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping("/myInfo/{id}")
    public ResponseEntity<GetUserInfoDTO> getUserInfo(@PathVariable long id) {
        GetUserInfoDTO userInfo = userInfoService.findUserById(id);
        return ResponseEntity.ok(userInfo);
    }
}
