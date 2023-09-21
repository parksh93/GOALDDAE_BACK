package com.goalddae.service;


import com.goalddae.dto.admin.DeleteAdminDTO;
import com.goalddae.dto.admin.GetAdminListDTO;
import com.goalddae.dto.email.SendEmailDTO;
import com.goalddae.dto.user.*;
import com.goalddae.entity.User;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletResponse;


public interface UserService {
    public void save(User user);
    public boolean generateTokenFromLogin(LoginDTO loginDTO, HttpServletResponse response);
    //    public boolean validToken(String token, String refreshTokenCookie, HttpServletResponse response);
    public GetUserInfoDTO getUserInfo(String token);
    public GetUserInfoDTO getFriendInfo(long id);
    public boolean checkLoginId(CheckLoginIdDTO checkLoginIdDTO);
    public boolean checkEmail(SendEmailDTO checkEmailDTO);
    public boolean checkNickname(CheckNicknameDTO checkNicknameDTO);
    public void update(GetUserInfoDTO getUserInfoDTO);
    public void updateProfileImg(GetUserInfoDTO getUserInfoDTO, MultipartFile multipartFile);
    public void updateSocialSignup(GetUserInfoDTO getUserInfoDTO);
    public void updateTeamId(GetUserInfoDTO getUserInfoDTO);
    public ResponseFindLoginIdDTO getLoginIdByEmailAndName(RequestFindLoginIdDTO requestFindLoginIdDTO);
    public boolean checkLoginIdAndEmail(RequestFindPasswordDTO requestFindPasswordDTO, HttpServletResponse response);
    public boolean changePassword(ChangePasswordDTO changePasswordDTO);
    public void changePasswordInMypage(ChangePasswordInMypageDTO changePasswordInMypageDTO);
    public User findByEmail(String email);
    public void deleteUser(long id);
    public void updateLevel(GetUserInfoDTO getUserInfoDTO);
    // 팀장인지 확인 유무
    boolean checkIfTeamLeader(Long userId);
}

