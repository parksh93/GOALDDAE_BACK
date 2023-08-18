package com.goalddae.service;

import com.goalddae.dto.email.SendEmailDTO;
import com.goalddae.dto.user.*;
import com.goalddae.entity.CommunicationBoard;
import com.goalddae.entity.UsedTransactionBoard;
import com.goalddae.entity.User;
import com.goalddae.repository.UserJPARepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserJPARepository userJPARepository;

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
    @DisplayName("유저정보 수정 테스트")
    public void updateTest() {
        // given
        String loginId = "수정아이디";
        String nickname = "수정닉네임";
        String email = "abc@abc.com";

        GetUserInfoDTO getUserInfoDTO = GetUserInfoDTO.builder()
                .loginId(loginId)
                        .nickname(nickname)
                                .email(email)
                                        .build();

        // when
        userService.update(getUserInfoDTO);

        // then
        User updatedUser = userJPARepository.findByLoginId(loginId);
        assertEquals("abc@abc.com", updatedUser.getEmail());
        assertEquals("수정닉네임", updatedUser.getNickname());
    }
    @Test
    @Transactional
    @DisplayName("자유게시판에 쓴 글 조회 테스트")
    public void getUserCommunicationBoardPostsTest() {
        // given
        long userId = 1;

        // when
        List<CommunicationBoard> communicationBoardList = userService.getUserCommunicationBoardPosts(userId);

        // then
        assertEquals(0, communicationBoardList.size());
    }
    @Test
    @Transactional
    @DisplayName("중고거래게시판에 쓴 글 조회 테스트")
    public void getUserUsedTransactionBoardPostsTest() {
        // given
        long userId = 1;

        // when
        List<UsedTransactionBoard> usedTransactionBoardList = userService.getUserUsedTransactionBoardPosts(userId);

        // then
        assertEquals(0, usedTransactionBoardList.size());
    }

}
