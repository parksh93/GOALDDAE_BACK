package com.goalddae.service;

import com.goalddae.dto.user.GetUserInfoDTO;
import com.goalddae.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {

    UserInfoRepository userInfoRepository;

    @Autowired
    public UserInfoService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public GetUserInfoDTO findUserById(long id) {
        return userInfoRepository.findUserById(id);
    }
}
