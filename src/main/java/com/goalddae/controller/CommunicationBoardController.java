package com.goalddae.controller;

import com.goalddae.entity.CommunicationBoard;
import com.goalddae.service.CommunicationBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class CommunicationBoardController {
    private final CommunicationBoardService communicationBoardService;

    @Autowired
    public CommunicationBoardController(CommunicationBoardService communicationBoardService) {
        this.communicationBoardService = communicationBoardService;
    }

    @GetMapping("/user/{writer}")
    public List<CommunicationBoard> getUserPosts(@PathVariable String writer) {
        return communicationBoardService.getUserPosts(writer);
    }
}
