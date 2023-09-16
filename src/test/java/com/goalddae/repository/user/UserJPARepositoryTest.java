package com.goalddae.repository.user;

import com.goalddae.entity.User;
import com.goalddae.repository.UserJPARepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserJPARepositoryTest {
    @Autowired
    UserJPARepository userJPARepository;

    @Test
    @Transactional
    @DisplayName("로그인아이디를 이용한 유저 정보 조회")
    public void finByLoginIdTest(){
        String loginId = "asdas";

        User user = userJPARepository.findByLoginId(loginId);

        assertEquals("박상현", user.getName());
//        assertEquals(1, user.getId());
    }

    @Test
    @Transactional
    @DisplayName("아이디 갯수 체크")
    public void countByLoginIdTest(){
        String loginId = "asd";
        int checkLoginIdCnt = userJPARepository.countByLoginId(loginId);


        assertEquals(1, checkLoginIdCnt);
    }

    @Test
    @Transactional
    @DisplayName("이메일 갯수 체크")
    public void countByEmailTest() {
        String email = "jsss@naver.com";
        int checkEmailCnt = userJPARepository.countByEmail(email);


        assertEquals(0, checkEmailCnt);
    }

    @Test
    @Transactional
    @DisplayName("닉네임 갯수 체크")
    public void countByNicknameTest() {
        String nickname = "새로운 닉네임";
        int checkNicknameCnt = userJPARepository.countByNickname(nickname);

        assertEquals(0, checkNicknameCnt);
    }

    @Test
    @Transactional
    @DisplayName("이메일과 닉네임을 통해 로그인 아이디 찾기")
    public void findLoginIdByEmailAndName () {
        String email = "jsap50@naver.com";
        String name = "박상현";

        String loginId = userJPARepository.findLoginIdByEmailAndName(email, name);

        assertEquals("asd", loginId);
    }
    @Test
    @Transactional
    @DisplayName("로그인 아이디와 이메일에 해당하는 정보의 갯수 가져오기")
    public void countByLoginIdAndEmailTest() {
        String loginId = "asdas";
        String email = "jsap50@naver.com";

        int userCnt = userJPARepository.countByLoginIdAndEmail(loginId, email);

        assertEquals(1, userCnt);
    }

    @Test
    @Transactional
    @DisplayName("이메일을 통해 아이디 찾기")
    public void findByEmail() {
        String email = "jsap50@naver.com";

        User user = userJPARepository.findByEmail(email);

        assertEquals(user.getLoginId(), "asdas");
    }

    @Test
    @Transactional
    @DisplayName("관리자만 조회")
    public void findByAuthorityTest(){
        String authority = "admin";

        List<User> userList = userJPARepository.findByAuthority(authority);

        assertEquals("박상현", userList.get(0).getName());
    }
}
