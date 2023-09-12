package com.goalddae.service;


import com.goalddae.dto.email.SendEmailDTO;
import com.goalddae.dto.user.*;
import com.goalddae.entity.CommunicationBoard;
import com.goalddae.entity.UsedTransactionBoard;
import com.goalddae.entity.User;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import com.goalddae.dto.email.SendEmailDTO;
import com.goalddae.dto.user.*;
import com.goalddae.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;


public interface UserService {
    public void save(User user);
    public User getByCredentials(String loginId);
    public boolean generateTokenFromLogin(LoginDTO loginDTO, HttpServletResponse response);
//    public boolean validToken(String token, String refreshTokenCookie, HttpServletResponse response);
    public GetUserInfoDTO getUserInfo(String token);
    public boolean checkLoginId(CheckLoginIdDTO checkLoginIdDTO);
    public boolean checkEmail(SendEmailDTO checkEmailDTO);
    public boolean checkNickname(CheckNicknameDTO checkNicknameDTO);
    public void update(GetUserInfoDTO getUserInfoDTO);
    public void updateProfileImg(GetUserInfoDTO getUserInfoDTO, MultipartFile multipartFile);
    public void updateSocialSignup(GetUserInfoDTO getUserInfoDTO);
    public ResponseFindLoginIdDTO getLoginIdByEmailAndName(RequestFindLoginIdDTO requestFindLoginIdDTO);
    public boolean checkLoginIdAndEmail(RequestFindPasswordDTO requestFindPasswordDTO, HttpServletResponse response);
    public boolean changePassword(ChangePasswordDTO changePasswordDTO);
    public User findByEmail(String email);
    public void deleteUser(long id);
}
