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

    @DeleteMapping("/user/{boardId}")
    public ResponseEntity<String> deleteByUser(@PathVariable Long boardId) {
        replyService.deleteByUser(boardId);
        return ResponseEntity.ok("댓글이 유저에 의해 삭제되었습니다.");
    }

    @DeleteMapping("/admin/{boardId}")
    public ResponseEntity<String> deleteByAdmin(@PathVariable Long boardId) {
        replyService.deleteByAdmin(boardId);
        return ResponseEntity.ok("게시글이 관리자에 의해 삭제되었습니다.");
    }


    @PostMapping("/save")
    public ResponseEntity<String> saveReplry(@RequestBody CommunicationReply communicationReply) {
        replyService.save(communicationReply);
        return ResponseEntity.ok("댓글이 저장되었습니다.");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateBoard(@RequestBody CommunicationReply communicationReply) {
        replyService.update(communicationReply);
        return ResponseEntity.ok("게시글이 수정되었습니다.");
    }



}
