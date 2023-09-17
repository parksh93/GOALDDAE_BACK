package com.goalddae.controller;

import com.goalddae.dto.admin.*;
import com.goalddae.dto.user.GetUserInfoDTO;
import com.goalddae.dto.user.SaveUserInfoDTO;
import com.goalddae.entity.SoccerField;
import com.goalddae.service.AdminService;
import com.goalddae.service.SoccerFieldService;
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
    private SoccerFieldService soccerFieldService;

    @Autowired
    public AdminController(AdminService adminService, UserService userService, SoccerFieldService soccerFieldService){
        this.adminService = adminService;
        this.userService = userService;
        this.soccerFieldService = soccerFieldService;
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

    @RequestMapping(value = "/getManagerInfo", method = RequestMethod.POST)
    public ResponseEntity<GetUserInfoDTO> getMangerInfo (@CookieValue(required = false) String token){
        GetUserInfoDTO userInfoDTO = userService.getUserInfo(token);

        return ResponseEntity.ok(userInfoDTO);
    }

    @RequestMapping("/getManagerList")
    public List<GetAdminListDTO> getMangerList(){
        List<GetAdminListDTO> managerList = adminService.findByAuthority("manager");

        return managerList;
    }

    @RequestMapping(value = "/saveManager", method = RequestMethod.PUT)
    public void saveManager(@RequestBody SaveUserInfoDTO user){
        adminService.saveManager(user);
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

    @GetMapping("/getReportReply")
    public List<GetReportReplyDTO> getReportReply() {
        return adminService.findReportReply();
    }

    @DeleteMapping("/approvalReplyReport")
    public void approvalReplyReport(@RequestBody ReplyReportProcessDTO replyReportProcessDTO){
        System.out.println(replyReportProcessDTO.toString());
        adminService.approvalReplyReport(replyReportProcessDTO);
    }

    @DeleteMapping("/notApprovalReplyReport")
    public void notApprovalReplyReport(@RequestBody ReplyReportProcessDTO replyReportProcessDTO){
        adminService.notApprovalReplyReport(replyReportProcessDTO);
    }

    @GetMapping("/getSoccerFieldList")
    public List<SoccerField> getSoccerFieldList(){
        return soccerFieldService.getSoccerFieldList();
    }
}
