package com.goalddae.service;


import com.goalddae.dto.email.SendEmailDTO;
import com.goalddae.dto.user.CheckLoginIdDTO;
import com.goalddae.dto.user.CheckNicknameDTO;
import com.goalddae.dto.user.GetUserInfoDTO;
import com.goalddae.dto.user.LoginDTO;
import com.goalddae.entity.CommunicationBoard;
import com.goalddae.entity.UsedTransactionBoard;
import com.goalddae.entity.User;

import java.util.List;

public interface UserService {
    public void save(User user);
    public User getByCredentials(String loginId);
    public String generateTokenFromLogin(LoginDTO loginDTO);
    public GetUserInfoDTO getUserInfo(String token);
    public boolean checkLoginId(CheckLoginIdDTO checkLoginIdDTO);
    public boolean checkEmail(SendEmailDTO checkEmailDTO);
    public boolean checkNickname(CheckNicknameDTO checkNicknameDTO);
    public void update(User user);
    public List<CommunicationBoard> getUserCommunicationBoardPosts(long userId);
    public List<UsedTransactionBoard> getUserUsedTransactionBoardPosts(long userId);

}
