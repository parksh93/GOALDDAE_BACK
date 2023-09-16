package com.goalddae.service;

import com.goalddae.dto.admin.DeleteAdminDTO;
import com.goalddae.dto.admin.GetAdminListDTO;
import com.goalddae.dto.admin.BoardReportProcessDTO;
import com.goalddae.dto.admin.GetReportBoardDTO;
import com.goalddae.dto.user.SaveUserInfoDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AdminServiceTest {
    @Autowired
    AdminService adminService;

    @Test
    @Transactional
    @DisplayName("관리자 조회")
    public void getAdminListTest() {
        String auth = "admin";
        List<GetAdminListDTO> adminList = adminService.findByAuthority(auth);

        assertEquals("2023-09-14", adminList.get(0).getSignUpDate());
    }

    @Test
    @Transactional
    @DisplayName("관리자 추가")
    public void saveAdminTest() {
        SaveUserInfoDTO user = SaveUserInfoDTO.builder()
                .loginId("qweqw")
                .password("1234")
                .name("새관리자")
                .email("weq@nave.com")
                .phoneNumber("010-2321-2312")
                .build();

        adminService.saveAdmin(user);
    }

    @Test
    @Transactional
    @DisplayName("관리자 삭제")
    public void deleteAdmin() {
        DeleteAdminDTO deleteAdminDTO = DeleteAdminDTO.builder()
                .deleteAdminList(List.of(10L)).build();

        adminService.deleteAdmin(deleteAdminDTO);

        String auth = "admin";
        List<GetAdminListDTO> adminList = adminService.findByAuthority(auth);

        assertEquals(3, adminList.size());
    }

    @Test
    @Transactional
    @DisplayName("신고 게시물 조회")
    public void findReportBoard() {
        List<GetReportBoardDTO> reportBoardList = adminService.findReportBoard();

        assertEquals("국ㄹ", reportBoardList.get(0).getReportUser());
    }

    @Test
    @Transactional
    @DisplayName("신고 게시글 승인")
    public void approvalReport() {
        long boardId = 3;

        BoardReportProcessDTO reportProcessDTO = BoardReportProcessDTO.builder().boardList(List.of(boardId)).build();

        adminService.approvalBoardReport(reportProcessDTO);
    }

    @Test
    @Transactional
    @DisplayName("신고 게시글 승인")
    public void notApprovalReport() {
        long boardId = 3;

        BoardReportProcessDTO reportProcessDTO = BoardReportProcessDTO.builder().boardList(List.of(boardId)).build();
        adminService.notApprovalBoardReport(reportProcessDTO);
    }
}
