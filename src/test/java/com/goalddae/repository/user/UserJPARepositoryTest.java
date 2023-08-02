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
    public void finByLoginId(){
        String loginId = "asd";

        User user = userJPARepository.findByLoginId(loginId);

        assertEquals("박상현", user.getNickname());
        assertEquals(1, user.getId());
    }
}
