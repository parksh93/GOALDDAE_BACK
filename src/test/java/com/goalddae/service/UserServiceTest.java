package com.goalddae.service;

import com.goalddae.dto.user.LoginDTO;
import com.goalddae.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserServiceImpl userService;

    @Test
    @Transactional
    @DisplayName("로그인 아이디를 이용한 유저 정보 조회")
    public void getByCredentialsTest(){
        String loginId = "asd";

        User user = userService.getByCredentials(loginId);

        assertEquals(1, user.getId());
        assertEquals("박상현", user.getNickname());
    }

    @Test
    @Transactional
    @DisplayName("로그인 정보 확인 및 토큰발행")
    public void generateTokenFromLoginTest() {
        LoginDTO loginDTO = LoginDTO.builder()
                .loginId("asd")
                .password("123")
                .build();

        String token = userService.generateTokenFromLogin(loginDTO);

        assertNotEquals("", token) ;
    }
}
