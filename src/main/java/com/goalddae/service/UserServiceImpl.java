package com.goalddae.service;

import com.goalddae.config.jwt.TokenProvider;
import com.goalddae.dto.email.SendEmailDTO;
import com.goalddae.dto.user.CheckLoginIdDTO;
import com.goalddae.dto.user.CheckNicknameDTO;
import com.goalddae.dto.user.GetUserInfoDTO;
import com.goalddae.dto.user.LoginDTO;
import com.goalddae.entity.User;
import com.goalddae.exception.NotFoundUserException;
import com.goalddae.repository.UserJPARepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService{
    private final UserJPARepository userJPARepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenProvider tokenProvider;


    @Autowired
    public UserServiceImpl(UserJPARepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, TokenProvider tokenProvider){
        this.userJPARepository = userRepository;
        this.bCryptPasswordEncoder =bCryptPasswordEncoder;
        this.tokenProvider = tokenProvider;
    }

    public void save(User user){
        User newUser = User.builder()
                .loginId(user.getLoginId())
                .email(user.getEmail())
                .password(bCryptPasswordEncoder.encode(user.getPassword()))
                .nickname(user.getNickname())
                .gender(user.getGender())
                .phoneNumber(user.getPhoneNumber())
                .birth(user.getBirth())
                .preferredCity(user.getPreferredCity())
                .preferredArea(user.getPreferredArea())
                .activityClass(user.getActivityClass())
                .authority(user.getAuthority())
                .userCode(createUserCode())
                .build();

        userJPARepository.save(newUser);
    }

    public static String createUserCode() {
        StringBuffer code = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 6; i++) {
            int index = rnd.nextInt(3);

            switch (index) {
                case 0:
                    code.append((char) ((int) (rnd.nextInt(26)) + 97));
                    break;
                case 1:
                    code.append((char) ((int) (rnd.nextInt(26)) + 65));
                    break;
                case 2:
                    code.append((rnd.nextInt(10)));
                    break;
            }
        }

        return code.toString();
    }

    public User getByCredentials(String loginId){
        return userJPARepository.findByLoginId(loginId);
    }

    // ****회원가입 구현 후 인코딩 코드 추가 예정
    public String generateTokenFromLogin(LoginDTO loginDTO){
        try {
            User userInfo = getByCredentials(loginDTO.getLoginId());

            if (bCryptPasswordEncoder.matches(loginDTO.getPassword(), userInfo.getPassword())) {
                String token = tokenProvider.generateToken(userInfo, Duration.ofHours(2));

                return token;
            } else {
                throw new NotFoundUserException("login fail");
            }
        } catch (Exception e) {
            return "";
        }
    }

    public GetUserInfoDTO getUserInfo(String token){
        if(tokenProvider.validToken(token)){
           User user = userJPARepository.findById(tokenProvider.getUserId(token)).get();

           GetUserInfoDTO userInfoDTO = new GetUserInfoDTO(user);

           return userInfoDTO;
        }
        return null;
    }

    public boolean checkLoginId(CheckLoginIdDTO checkLoginIdDTO){
        long checkLoginIdCnt = userJPARepository.countByLoginId(checkLoginIdDTO.getLoginId());

        if(checkLoginIdCnt == 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkEmail(SendEmailDTO checkEmailDTO){
        long checkEmailCnt = userJPARepository.countByEmail(checkEmailDTO.getEmail());

        if(checkEmailCnt == 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkNickname(CheckNicknameDTO checkNicknameDTO){
        long checkNicknameCnt = userJPARepository.countByNickname(checkNicknameDTO.getNickname());

        if(checkNicknameCnt == 0){
            return true;
        }else{
            return false;
        }
    }
}
