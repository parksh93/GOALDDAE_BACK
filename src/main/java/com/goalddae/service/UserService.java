package com.goalddae.service;

<<<<<<< HEAD
=======
import com.goalddae.dto.email.SendEmailDTO;
import com.goalddae.dto.user.CheckLoginIdDTO;
import com.goalddae.dto.user.CheckNicknameDTO;
>>>>>>> 71a2f049829b4878df4ce759f50a22f2738ffdc0
import com.goalddae.dto.user.GetUserInfoDTO;
import com.goalddae.dto.user.LoginDTO;
import com.goalddae.entity.User;

public interface UserService {
    public void save(User user);
    public User getByCredentials(String loginId);
    public String generateTokenFromLogin(LoginDTO loginDTO);
    public GetUserInfoDTO getUserInfo(String token);
    public void update(User user);

}
