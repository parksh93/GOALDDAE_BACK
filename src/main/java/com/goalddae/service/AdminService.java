package com.goalddae.service;

import com.goalddae.dto.admin.DeleteAdminDTO;
import com.goalddae.dto.admin.GetAdminListDTO;
import com.goalddae.dto.admin.BoardReportProcessDTO;
import com.goalddae.dto.admin.GetReportBoardDTO;
import com.goalddae.dto.user.SaveUserInfoDTO;

import java.util.List;

public interface AdminService {
    public List<GetAdminListDTO> findByAuthority(String authority);
    public void saveAdmin(SaveUserInfoDTO user);
    public void deleteAdmin(DeleteAdminDTO deleteAdminDTO);
    List<GetReportBoardDTO> findReportBoard();
    void approvalBoardReport(BoardReportProcessDTO reportProcessDTO);
    void notApprovalBoardReport(BoardReportProcessDTO reportProcessDTO);

}
