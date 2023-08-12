package com.goalddae.repository.user;

import com.goalddae.entity.User;
import com.goalddae.repository.UserJPARepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserJPARepositoryTest {
    @Autowired
    UserJPARepository userJPARepository;

    @Test
    @Transactional
    @DisplayName("로그인아이디를 이용한 유저 정보 조회")
    public void finByLoginIdTest(){
        String loginId = "asd";

        User user = userJPARepository.findByLoginId(loginId);

        assertEquals("박상현", user.getNickname());
        assertEquals(1, user.getId());
    }

    @Test
    @Transactional
    @DisplayName("아이디 갯수 체크")
    public void countByLoginIdTest(){
        String loginId = "asd";

        long checkLoginIdCnt = userJPARepository.countByLoginId(loginId);

        assertEquals(1, checkLoginIdCnt);
    }

    @Test
    @Transactional
    @DisplayName("이메일 갯수 체크")
    public void countByEmailTest() {
        String email = "jsss@naver.com";

        long checkEmailCnt = userJPARepository.countByEmail(email);

        assertEquals(0, checkEmailCnt);
    }

    @Test
    @Transactional
    @DisplayName("닉네임 갯수 체크")
    public void countByNicknameTest() {
        String nickname = "새로운 닉네임";

        long checkNicknameCnt = userJPARepository.countByNickname(nickname);

        assertEquals(0, checkNicknameCnt);
    }

    @Test
    @Transactional
    @DisplayName("이메일과 닉네임을 통해 로그인 아이디 찾기")
    public void findeLoginIdByEmailTest () {
        String email = "jsap50@naver.com";
        String nickname = "박상현";

        String loginId = userJPARepository.findLoginIdByEmailAndNickname(email, nickname);

        assertEquals("asd", loginId);
    }
}
