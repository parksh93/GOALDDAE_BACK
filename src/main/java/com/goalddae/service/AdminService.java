package com.goalddae.service;

import com.goalddae.dto.admin.*;
import com.goalddae.dto.user.SaveUserInfoDTO;

import java.util.List;

public interface AdminService {
    public List<GetAdminListDTO> findByAuthority(String authority);
    public void saveAdmin(SaveUserInfoDTO user);
    public void deleteAdmin(DeleteAdminDTO deleteAdminDTO);
    public void saveManager(SaveUserInfoDTO user);
    List<GetReportBoardDTO> findReportBoard();
    void approvalBoardReport(BoardReportProcessDTO boardReportProcessDTO);
    void notApprovalBoardReport(BoardReportProcessDTO boardReportProcessDTO);
    List<GetReportReplyDTO> findReportReply();
    void approvalReplyReport(ReplyReportProcessDTO replyReportProcessDTO);
    void notApprovalReplyReport(ReplyReportProcessDTO replyReportProcessDTO);

    List<GetUserListDTO> findUserList(String authority);
}
