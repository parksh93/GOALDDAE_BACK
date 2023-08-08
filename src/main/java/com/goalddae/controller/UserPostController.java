package com.goalddae.controller;

import com.goalddae.dto.post.UserPostsResponse;
import com.goalddae.entity.CommunicationBoard;
import com.goalddae.entity.UsedTransactionBoard;
import com.goalddae.exception.NotFoundPostException;
import com.goalddae.service.UserPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("mypage/posts")
public class UserPostController {
    private final UserPostService userPostService;
    @Autowired
    public UserPostController(UserPostService userPostService) {
        this.userPostService = userPostService;
    }

    // 내가 쓴 글 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserPostsResponse> getUserPosts(@PathVariable long id) {

        try {
            List<CommunicationBoard> communicationBoardPosts = userPostService.getUserCommunicationBoardPosts(id);
            List<UsedTransactionBoard> usedTransactionBoardPosts = userPostService.getUserUsedTransactionBoardPosts(id);

            UserPostsResponse response = new UserPostsResponse(communicationBoardPosts, usedTransactionBoardPosts);
            return ResponseEntity.ok(response);

        } catch (NotFoundPostException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
}
