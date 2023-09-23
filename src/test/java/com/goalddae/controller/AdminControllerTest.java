package com.goalddae.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goalddae.dto.admin.DeleteAdminDTO;
import com.goalddae.entity.User;
import com.goalddae.repository.UserJPARepository;
import org.junit.jupiter.api.BeforeEach;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    UserJPARepository userJPARepository;

    @BeforeEach
    void setAuthUser() {
        userJPARepository.deleteAll();

        User user = User.builder()
                .authority("admin")
                .loginId("asda12312s")
                .email("1234")
                .name("박상현")
                .signupDate(LocalDateTime.now())
                .profileUpdateDate(LocalDateTime.now())
                .build();

        userJPARepository.save(user);
    }


    @Test
    @Transactional
    @DisplayName("관리자 리스트 조회")
    public void getAdminListTest() throws Exception{
        String url = "/admin/getAdminList";

        ResultActions result = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("박상현"));
    }

    @Test
    @Transactional
    @DisplayName("새로운 관리자 추가")
    public void saveAdminTest() throws Exception {
        User user = User.builder()
                .loginId("qweqw")
                .password("1234")
                .name("새관리자")
                .email("weq@nave.com")
                .phoneNumber("010-2321-2312")
                .build();
        String url = "/admin/saveAdmin";
        String url2 = "/admin/getAdminList";

        final String request = objectMapper.writeValueAsString(user);

        mockMvc.perform(post(url).content(request).contentType(MediaType.APPLICATION_JSON));

        ResultActions result = mockMvc.perform(get(url2).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[1].name").value("새관리자"));
    }

    @Test
    @Transactional
    @DisplayName("관리자 삭제")
    public void deleteAdmin() throws Exception {
        DeleteAdminDTO deleteAdminDTO = DeleteAdminDTO
                .builder().deleteAdminList(List.of(10L)).build();
        String url = "/admin/deleteAdmin";
        String url2 = "/admin/getAdminList";

        final String request = objectMapper.writeValueAsString(deleteAdminDTO);

        mockMvc.perform(post(url).content(request).contentType(MediaType.APPLICATION_JSON));

        ResultActions result = mockMvc.perform(get(url2).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.length").value(3));
    }
}
