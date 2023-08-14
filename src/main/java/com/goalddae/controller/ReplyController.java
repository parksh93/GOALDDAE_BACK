package com.goalddae.controller;

import com.goalddae.entity.CommunicationBoard;
import com.goalddae.entity.CommunicationReply;
import com.goalddae.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reply")
public class ReplyController {

    ReplyService replyService;

    @Autowired
    public ReplyController(ReplyService replyService){
        this.replyService = replyService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveReply(@RequestBody CommunicationReply communicationReply) {
        replyService.save(communicationReply);
        return ResponseEntity.ok("댓글이 저장되었습니다.");
    }



}
