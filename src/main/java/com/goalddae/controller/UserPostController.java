package com.goalddae.controller;

import com.goalddae.dto.post.UserPostsResponse;
import com.goalddae.entity.CommunicationBoard;
import com.goalddae.entity.UsedTransactionBoard;
import com.goalddae.service.UserPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class UserPostController {
    private final UserPostService userPostService;
    @Autowired
    public UserPostController(UserPostService userPostService) {
        this.userPostService = userPostService;
    }

    // 내가 쓴 글 조회
    @GetMapping("/user/{id}")
    public UserPostsResponse getUserPosts(@PathVariable long id) {
        List<CommunicationBoard> communicationBoardPosts = userPostService.getUserCommunicationBoardPosts(id);
        List<UsedTransactionBoard> usedTransactionBoardPosts = userPostService.getUserUsedTransactionBoardPosts(id);

        return new UserPostsResponse(communicationBoardPosts, usedTransactionBoardPosts);
    }
}
