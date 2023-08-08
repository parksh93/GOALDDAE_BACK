package com.goalddae.service;

import com.goalddae.config.jwt.TokenProvider;
import com.goalddae.dto.user.GetUserInfoDTO;
import com.goalddae.dto.user.LoginDTO;
import com.goalddae.entity.User;
import com.goalddae.exception.NotFoundUserException;
import com.goalddae.repository.UserJPARepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

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
                .email(user.getEmail())
                .password(bCryptPasswordEncoder.encode(user.getPassword()))
                .nickname(user.getNickname())
                .gender(user.getGender())
                .profileImgUrl(user.getProfileImgUrl())
                .phoneNumber(user.getPhoneNumber())
                .birth(user.getBirth())
                .preferredCity(user.getPreferredCity())
                .preferredArea(user.getPreferredArea())
                .build();

        userJPARepository.save(newUser);
    }

    public User getByCredentials(String loginId){
        return userJPARepository.findByLoginId(loginId);
    }

    // ****회원가입 구현 후 인코딩 코드 추가 예정
    public String generateTokenFromLogin(LoginDTO loginDTO){
        try {
            User userInfo = getByCredentials(loginDTO.getLoginId());

            if (userInfo.getPassword().equals(loginDTO.getPassword())) {
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
}
