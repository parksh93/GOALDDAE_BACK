package com.goalddae.service;

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
}