package com.goalddae.service;

import com.goalddae.dto.email.SendEmailDTO;
import com.goalddae.dto.user.*;
import com.goalddae.entity.User;

public interface UserService {
    public void save(User user);
    public User getByCredentials(String loginId);
    public String generateTokenFromLogin(LoginDTO loginDTO);
    public GetUserInfoDTO getUserInfo(String token);
    public boolean checkLoginId(CheckLoginIdDTO checkLoginIdDTO);
    public boolean checkEmail(SendEmailDTO checkEmailDTO);
    public boolean checkNickname(CheckNicknameDTO checkNicknameDTO);
    public String getLoginIdByEmailAndNickname(FindLoginIdDTO findLoginIdDTO);
}
