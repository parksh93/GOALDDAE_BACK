package com.goalddae.controller;

import com.goalddae.dto.board.ReplyListDTO;
import com.goalddae.entity.CommunicationBoard;
import com.goalddae.entity.CommunicationReply;
import com.goalddae.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reply")
public class ReplyController {

    ReplyService replyService;

    @Autowired
    public ReplyController(ReplyService replyService){
        this.replyService = replyService;
    }

    @GetMapping("/list/{boardId}")
    public ResponseEntity<Map<String, Object>> list(@PathVariable Integer boardId){

        List<ReplyListDTO> replyList = replyService.findAllByBoardId(boardId);

        Map<String, Object> response = new HashMap<>();
        response.put("replies", replyList);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveReplry(@RequestBody CommunicationReply communicationReply) {
        replyService.save(communicationReply);
        return ResponseEntity.ok("댓글이 저장되었습니다.");
    }



}
