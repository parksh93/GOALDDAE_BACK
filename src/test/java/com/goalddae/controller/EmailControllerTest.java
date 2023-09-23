package com.goalddae.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goalddae.dto.email.SendEmailDTO;
import com.goalddae.service.EmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmailControllerTest {
    @Autowired
    private EmailService emailService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    @DisplayName("이메일 발송")

    public void sendEmailSignupTest() throws Exception{
        SendEmailDTO sendEmailDTO = SendEmailDTO.builder()
                .email("sanghyun8527@gmail.com")
                .build();
        String url = "/sendEmailSignup";

        final String requestBody = objectMapper.writeValueAsString(sendEmailDTO);

        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));

    }
}
