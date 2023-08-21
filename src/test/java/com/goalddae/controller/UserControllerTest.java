package com.goalddae.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.goalddae.dto.user.*;
import com.goalddae.dto.user.CheckLoginIdDTO;
import com.goalddae.dto.user.CheckNicknameDTO;
import com.goalddae.dto.user.RequestFindLoginIdDTO;
import com.goalddae.dto.user.LoginDTO;
import com.goalddae.service.UserService;
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
public class UserControllerTest {
    @Autowired
    UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    @DisplayName("로그인 유저 확인 테스트")
    public void loginTest() throws Exception {
        LoginDTO loginDTO = LoginDTO.builder()
                .loginId("asd")
                .password("123")
                .build();
        String url = "/user/login";

        final String requestBody = objectMapper.writeValueAsString(loginDTO);

        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    @Transactional
    @DisplayName("로그인 아이디 중복 체크")
    public void checkLoginId() throws Exception{
        CheckLoginIdDTO checkLoginIdDTO = CheckLoginIdDTO.builder()
                .loginId("asd")
                .build();
        String url = "/user/checkLoginId";

        final String requestBody = objectMapper.writeValueAsString(checkLoginIdDTO);


        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
    }
    @Test
    @Transactional
    @DisplayName("닉네임 중복 체크")
    public void checkEmailTest() throws Exception{
        CheckNicknameDTO checkNicknameDTO = CheckNicknameDTO.builder()
                .nickname("박상현")
                .build();
        String url = "/user/checkNickname";

        final String requestBody = objectMapper.writeValueAsString(checkNicknameDTO);


        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
    }

    @Test
    @Transactional
    @DisplayName("로그인 아이디 찾기")
    public void findLoginIdTest() throws Exception {
        String email = "jsap50@naver.com";
        String name = "박상현";
        String url = "/user/findLoginId";

        RequestFindLoginIdDTO findLoginIdDTO = RequestFindLoginIdDTO.builder()
                .email(email)
                .name(name)
                .build();
        final String requestbody = objectMapper.writeValueAsString(findLoginIdDTO);

        ResultActions result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestbody)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$").value("asd"));
    }

    @Test
    @Transactional
    @DisplayName("비밀번호 찾기")
    public void findPasswordTest() throws Exception{
        String loginId = "asdas";
        String email = "jsap50@naver.com";
        String url = "/user/findPassword";

        RequestFindPasswordDTO findPasswordDTO = RequestFindPasswordDTO.builder()
                .loginId(loginId)
                .email(email)
                .build();

        final String request = objectMapper.writeValueAsString(findPasswordDTO);

        ResultActions result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(request)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(true));
    }
}
