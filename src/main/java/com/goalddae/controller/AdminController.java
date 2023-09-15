package com.goalddae.controller;

import com.goalddae.dto.admin.DeleteAdminDTO;
import com.goalddae.dto.admin.GetAdminListDTO;
import com.goalddae.dto.user.GetUserInfoDTO;
import com.goalddae.dto.user.SaveUserInfoDTO;
import com.goalddae.entity.User;
import com.goalddae.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/saveAdmin", method = RequestMethod.POST)
    public void saveAdmin(@RequestBody SaveUserInfoDTO user){
        userService.saveAdmin(user);
    }

    @DeleteMapping("/deleteAdmin")
    public void deleteAdmin(@RequestBody DeleteAdminDTO deleteAdminDTO){
        userService.deleteAdmin(deleteAdminDTO);
    }

}
