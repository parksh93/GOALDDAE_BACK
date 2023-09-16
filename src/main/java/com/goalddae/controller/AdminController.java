package com.goalddae.controller;

import com.goalddae.dto.admin.BoardReportProcessDTO;
import com.goalddae.dto.admin.DeleteAdminDTO;
import com.goalddae.dto.admin.GetAdminListDTO;
import com.goalddae.dto.admin.GetReportBoardDTO;
import com.goalddae.dto.user.GetUserInfoDTO;
import com.goalddae.dto.user.SaveUserInfoDTO;
import com.goalddae.service.AdminService;
import com.goalddae.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private AdminService adminService;
    private UserService userService;

    @Autowired
    public AdminController(AdminService adminService, UserService userService){
        this.adminService = adminService;
        this.userService = userService;
    }

    @RequestMapping(value = "/getAdminInfo", method = RequestMethod.POST)
    public ResponseEntity<GetUserInfoDTO> getAdminInfo (@CookieValue(required = false) String token){
        GetUserInfoDTO userInfoDTO = userService.getUserInfo(token);

        return ResponseEntity.ok(userInfoDTO);
    }

    @RequestMapping("/getAdminList")
    public List<GetAdminListDTO> getAdminList(){
        List<GetAdminListDTO> adminList = adminService.findByAuthority("admin");

        return adminList;
    }

    @RequestMapping(value = "/saveAdmin", method = RequestMethod.PUT)
    public void saveAdmin(@RequestBody SaveUserInfoDTO user){
        adminService.saveAdmin(user);
    }

    @DeleteMapping("/deleteAdmin")
    public void deleteAdmin(@RequestBody DeleteAdminDTO deleteAdminDTO){
        adminService.deleteAdmin(deleteAdminDTO);
    }

    @GetMapping("/getReportBoard")
    public List<GetReportBoardDTO> getReportBoard(){
        return adminService.findReportBoard();
    }

    @DeleteMapping("/approvalBoardReport")
    public void approvalBoardReport(@RequestBody BoardReportProcessDTO boardReportProcessDTO){
        System.out.println(boardReportProcessDTO.toString());
        adminService.approvalBoardReport(boardReportProcessDTO);
    }

    @DeleteMapping("/notApprovalBoardReport")
    public void notApprovalBoardReport(@RequestBody BoardReportProcessDTO boardReportProcessDTO){
        adminService.notApprovalBoardReport(boardReportProcessDTO);
    }
}
