package com.goalddae.service;


import com.goalddae.dto.user.RequestFindPasswordDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class EmailServiceTest {
    @Autowired
    EmailService emailService;

    @Test
    @Transactional
    public void sendSimpleMessageTest() throws Exception {
        String key = emailService.sendSimpleMessage("sinh8492@naver.com");

        System.out.println(key);
    }

    @Test
    @Transactional
    @DisplayName("비밀번호 변경 이메일 발송")
    public void sendChangePasswordMessageTest() throws Exception {
        String loginId = "asdas";
        String email = "jsap50@naver.com";
        RequestFindPasswordDTO findPasswordDTO = RequestFindPasswordDTO.builder()
                        .loginId(loginId)
                                .email(email)
                                        .build();

        emailService.sendChangPasswordMessage(findPasswordDTO);
    }
}