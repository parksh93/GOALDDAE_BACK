package com.goalddae.service;

import com.goalddae.entity.User;
import com.goalddae.repository.UserJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private final UserJPARepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UsersService(UserJPARepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
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

        userRepository.save(newUser);
    }

    public User getByCredentials(String loginId){
        return userRepository.findByLoginId(loginId);
    }
}
