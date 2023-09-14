package com.goalddae.controller;

import com.goalddae.dto.admin.GetAdminListDTO;
import com.goalddae.dto.user.GetUserInfoDTO;
import com.goalddae.entity.User;
import com.goalddae.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;

    @Autowired
    public  AdminController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(value = "/getAdminInfo", method = RequestMethod.POST)
    public ResponseEntity<GetUserInfoDTO> getAdminInfo (@CookieValue(required = false) String token){
        GetUserInfoDTO userInfoDTO = userService.getUserInfo(token);

        return ResponseEntity.ok(userInfoDTO);
    }

    @RequestMapping("/getAdminList")
    public List<GetAdminListDTO> getAdminList(){
        List<GetAdminListDTO> adminList = userService.findByAuthority("admin");

        return adminList;
    }

}
