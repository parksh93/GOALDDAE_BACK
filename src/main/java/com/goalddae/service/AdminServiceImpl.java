package com.goalddae.service;

import com.goalddae.dto.admin.DeleteAdminDTO;
import com.goalddae.dto.admin.GetAdminListDTO;
import com.goalddae.dto.admin.BoardReportProcessDTO;
import com.goalddae.dto.admin.GetReportBoardDTO;
import com.goalddae.dto.user.SaveUserInfoDTO;
import com.goalddae.entity.User;
import com.goalddae.repository.BoardJPARepository;
import com.goalddae.repository.ReportedBoardJPARepository;
import com.goalddae.repository.UserJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{

    private BoardJPARepository boardJPARepository;
    private UserJPARepository userJPARepository;
    private ReportedBoardJPARepository reportedBoardJPARepository;

    @Autowired
    public AdminServiceImpl(BoardJPARepository boardJPARepository, UserJPARepository userJPARepository, ReportedBoardJPARepository reportedBoardJPARepository){
        this.boardJPARepository = boardJPARepository;
        this.userJPARepository = userJPARepository;
        this.reportedBoardJPARepository =reportedBoardJPARepository;
    }

    @Override
    public List<GetReportBoardDTO> findReportBoard() {
        return boardJPARepository.findReportBoard();
    }

    @Override
    public List<GetAdminListDTO> findByAuthority(String authority) {
        List<User> userList = userJPARepository.findByAuthority("admin");

        List<GetAdminListDTO> adminList = new ArrayList<>();

        for (User user:userList) {
            GetAdminListDTO getAdminListDTO = new GetAdminListDTO(user);

            String signUpDate = user.getSignupDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            getAdminListDTO.setSignUpDate(signUpDate);
            adminList.add(getAdminListDTO);
        }

        return adminList;
    }

    @Override
    public void saveAdmin(SaveUserInfoDTO user) {
        user.setNickname("관리자");
        user.setAuthority("admin");

        userJPARepository.save(user.toEntity());
    }

    @Override
    public void deleteAdmin(DeleteAdminDTO deleteAdminDTO) {
        List<Long> adminList = deleteAdminDTO.getDeleteAdminList();

        for (long id : adminList) {
            userJPARepository.deleteById(id);
        }
    }

    @Override
    public void approvalBoardReport(BoardReportProcessDTO boardReportProcessDTO) {
        for (long boardId:boardReportProcessDTO.getBoardList()) {
            boardJPARepository.deleteById(boardId);
            System.out.println("신고 내역 삭제");
            reportedBoardJPARepository.deleteByBoardId(boardId);
            System.out.println("게시글 삭제");
        }
    }

    @Override
    public void notApprovalBoardReport(BoardReportProcessDTO boardReportProcessDTO) {
        for (long boardId:boardReportProcessDTO.getBoardList()) {
            reportedBoardJPARepository.deleteByBoardId(boardId);
        }
    }

}
