package com.goalddae.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goalddae.entity.CommunicationBoard;
import com.goalddae.entity.UsedTransactionBoard;
import com.goalddae.service.UserPostService;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class UserPostControllerTest {

    @Autowired
    UserPostService userPostService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    public void getUserPostsTest() throws Exception {
        // given
        long id = 1;
        List<CommunicationBoard> communicationBoardPosts = new ArrayList<>();
        List<UsedTransactionBoard> usedTransactionBoardPosts = new ArrayList<>();

        // when
        when(userPostService.getUserCommunicationBoardPosts(id)).thenReturn(communicationBoardPosts);
        when(userPostService.getUserUsedTransactionBoardPosts(id)).thenReturn(usedTransactionBoardPosts);

        ResultActions resultActions = mockMvc.perform(get("/post/{id}", id)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.communicationBoardPosts").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.usedTransactionBoardPosts").isArray());
    }

}
