package com.goalddae.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goalddae.dto.user.CheckLoginIdDTO;
import com.goalddae.dto.user.CheckNicknameDTO;
import com.goalddae.dto.user.LoginDTO;
import com.goalddae.entity.CommunicationBoard;
import com.goalddae.entity.UsedTransactionBoard;
import com.goalddae.service.UserServiceImpl;
import com.goalddae.entity.User;
import com.goalddae.repository.UserJPARepository;
import com.goalddae.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    UserServiceImpl userService;

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
    @DisplayName("유저정보 수정 테스트")
    public void updateUserInfoTest() throws Exception {
        // given
        String nickname = "코로그졸귀";
        String level = "프로";

        User user = User.builder()
                .nickname(nickname)
                .level(level)
                .build();

        String url = "http://localhost:8080//updateUserInfo";
        String url2 = "/user/0/all"; // findAll 메서드 작성후, 테스트 적용 예정

        final String requestBody = objectMapper.writeValueAsString(user);

        // when
        mockMvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody));

        // then
        final ResultActions result = mockMvc.perform(get(url2)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nickname").value(nickname))
                .andExpect(jsonPath("$[0].level").value(level));
    }
    @Test
    @Transactional
    @DisplayName("내가 쓴 게시글 조회 테스트")
    public void getUserPostsTest() throws Exception {
        // given
        long id = 1;
        List<CommunicationBoard> communicationBoardPosts = new ArrayList<>();
        List<UsedTransactionBoard> usedTransactionBoardPosts = new ArrayList<>();

        // when
        when(userService.getUserCommunicationBoardPosts(id)).thenReturn(communicationBoardPosts);
        when(userService.getUserUsedTransactionBoardPosts(id)).thenReturn(usedTransactionBoardPosts);

        ResultActions resultActions = mockMvc.perform(get("/posts/{userId}", id)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.communicationBoardPosts").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.usedTransactionBoardPosts").isArray());
    }
}
