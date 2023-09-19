package com.goalddae.service;

import com.goalddae.dto.admin.*;
import com.goalddae.dto.user.SaveUserInfoDTO;
import com.goalddae.entity.CommunicationReply;
import com.goalddae.entity.User;
import com.goalddae.exception.UnValidUserException;
import com.goalddae.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{

    private BoardJPARepository boardJPARepository;
    private UserJPARepository userJPARepository;
    private ReportedBoardJPARepository reportedBoardJPARepository;
    private ReplyJPARepository replyJPARepository;
    private ReportedReplyJPARepository reportedReplyJPARepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AdminServiceImpl(BoardJPARepository boardJPARepository, UserJPARepository userJPARepository,
                            ReportedBoardJPARepository reportedBoardJPARepository, ReplyJPARepository replyJPARepository,
                            ReportedReplyJPARepository reportedReplyJPARepository, @Lazy BCryptPasswordEncoder bCryptPasswordEncoder){
        this.boardJPARepository = boardJPARepository;
        this.userJPARepository = userJPARepository;
        this.reportedBoardJPARepository =reportedBoardJPARepository;
        this.replyJPARepository = replyJPARepository;
        this.reportedReplyJPARepository = reportedReplyJPARepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<GetReportBoardDTO> findReportBoard() {
        return boardJPARepository.findReportBoard();
    }

    @Override
    public List<GetAdminListDTO> findByAuthority(String authority) {
        List<User> userList = userJPARepository.findByAuthority(authority);

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
        try{
            user.setNickname("관리자");
            user.setAuthority("admin");
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

            userJPARepository.save(user.toEntity());
        }catch (Exception e){
            throw new UnValidUserException("유효하지 않은 사용자 정보로 가입");
        }
    }

    @Override
    public void deleteAdmin(DeleteAdminDTO deleteAdminDTO) {
        List<Long> adminList = deleteAdminDTO.getDeleteAdminList();

        for (long id : adminList) {
            userJPARepository.deleteById(id);
        }
    }

    @Override
    public void saveManager(SaveUserInfoDTO user) {
        try{
            user.setNickname("매니저");
            user.setAuthority("manager");
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

            userJPARepository.save(user.toEntity());
        }catch (Exception e){
            throw new UnValidUserException("유효하지 않은 사용자 정보로 가입");
        }
    }

    @Override
    @Transactional
    public void approvalBoardReport(BoardReportProcessDTO boardReportProcessDTO) {
        for (long boardId:boardReportProcessDTO.getBoardList()) {
            boardJPARepository.deleteById(boardId);
            reportedBoardJPARepository.deleteByBoardId(boardId);
        }
    }

    @Override
    @Transactional
    public void notApprovalBoardReport(BoardReportProcessDTO boardReportProcessDTO) {
        for (long boardId:boardReportProcessDTO.getBoardList()) {
            reportedBoardJPARepository.deleteByBoardId(boardId);
        }
    }

    @Override
    public List<GetReportReplyDTO> findReportReply() {
        return replyJPARepository.findReportReply();
    }

    @Override
    @Transactional
    public void approvalReplyReport(ReplyReportProcessDTO replyReportProcessDTO) {
        for(long replyId:replyReportProcessDTO.getReplyList()){
            reportedReplyJPARepository.deleteByReplyId(replyId);
            CommunicationReply communicationReply = replyJPARepository.findById(replyId).get();
            communicationReply.deleteByAdmin();
        }
    }

    @Override
    @Transactional
    public void notApprovalReplyReport(ReplyReportProcessDTO replyReportProcessDTO) {
        for(long replyId:replyReportProcessDTO.getReplyList()){
            reportedReplyJPARepository.deleteByReplyId(replyId);
        }
    }

    @Override
    public List<GetUserListDTO> findUserList(String authority){
        List<User> userList = userJPARepository.findByAuthority(authority);
        List<GetUserListDTO> userListDTO = new ArrayList<>();
        for(User user: userList){
            GetUserListDTO getUserListDTO = new GetUserListDTO(user);
            String signUpDate = user.getSignupDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            getUserListDTO.setSignupDate(signUpDate);

            userListDTO.add(getUserListDTO);
        }

        return userListDTO;
    }
}
