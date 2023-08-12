package com.goalddae.service;

import com.goalddae.dto.email.SendEmailDTO;
import com.goalddae.dto.user.CheckLoginIdDTO;
import com.goalddae.dto.user.CheckNicknameDTO;
import com.goalddae.dto.user.FindLoginIdDTO;
import com.goalddae.dto.user.LoginDTO;
import com.goalddae.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    @Transactional
    @DisplayName("로그인 아이디 중복 체크")
    public void checkLoginIdTest(){
        CheckLoginIdDTO checkLoginIdDTO = CheckLoginIdDTO.builder().loginId("asd").build();

        boolean checkLoginId = userService.checkLoginId(checkLoginIdDTO);

        assertEquals(false, checkLoginId);
    }

    @Test
    @Transactional
    @DisplayName("이메일 중복 체크")
    public void checkEmailTest() {
        SendEmailDTO checkEmailDTO = SendEmailDTO.builder().email("aaa@gamil.com").build();

        boolean checkEmail = userService.checkEmail(checkEmailDTO);

        assertEquals(true, checkEmail);
    }

    @Test
    @Transactional
    @DisplayName("닉네임 중복 체크")
    public void checkNicknameTest() {
        CheckNicknameDTO checkNicknameDTO = CheckNicknameDTO.builder()
                .nickname("새로운 닉네임")
                .build();
        boolean checkNickname = userService.checkNickname(checkNicknameDTO);

        assertEquals(true, checkNickname);
    }

    @Test
    @Transactional
    @DisplayName("이메일과 닉네임을 통해 로그인 아이디 가져오기")
    public void getLoginIdByEmail(){
        String email = "asd@naver.com";
        String nickname = "박상현";

        FindLoginIdDTO findLoginIdDTO = FindLoginIdDTO.builder()
                .email(email).nickname(nickname).build();
        String loginId = userService.getLoginIdByEmailAndNickname(findLoginIdDTO);

        assertNull(loginId);
    }
}
