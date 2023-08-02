package com.goalddae.service;

import com.goalddae.entity.User;
import com.goalddae.repository.UserJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserJPARepository userJPARepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserJPARepository userJPARepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userJPARepository = userJPARepository;
        this.bCryptPasswordEncoder =bCryptPasswordEncoder;
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
}
