package com.goalddae.service;


import com.goalddae.dto.email.SendEmailDTO;
import com.goalddae.dto.user.*;
import com.goalddae.entity.CommunicationBoard;
import com.goalddae.entity.UsedTransactionBoard;
import com.goalddae.entity.User;

import java.util.List;

import com.goalddae.dto.email.SendEmailDTO;
import com.goalddae.dto.user.*;
import com.goalddae.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    public void save(User user);
    public User getByCredentials(String loginId);
    public String generateTokenFromLogin(LoginDTO loginDTO);
    public GetUserInfoDTO getUserInfo(String token);
    public boolean checkLoginId(CheckLoginIdDTO checkLoginIdDTO);
    public boolean checkEmail(SendEmailDTO checkEmailDTO);
    public boolean checkNickname(CheckNicknameDTO checkNicknameDTO);
    public void update(GetUserInfoDTO getUserInfoDTO);
    public List<CommunicationBoard> getUserCommunicationBoardPosts(long userId);
    public List<UsedTransactionBoard> getUserUsedTransactionBoardPosts(long userId);
    public ResponseFindLoginIdDTO getLoginIdByEmailAndName(RequestFindLoginIdDTO requestFindLoginIdDTO);
    public String checkLoginIdAndEmail(RequestFindPasswordDTO requestFindPasswordDTO);
    public boolean changePassword(ChangePasswordDTO changePasswordDTO);
}
