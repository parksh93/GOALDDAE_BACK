package com.goalddae.service;

import com.goalddae.repository.UserJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {
    private final UserJPARepository userJPARepository;

    @Autowired
    public UserDetailService(UserJPARepository userJPARepository){
        this.userJPARepository = userJPARepository;
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        return userJPARepository.findByLoginId(loginId);
    }
}
