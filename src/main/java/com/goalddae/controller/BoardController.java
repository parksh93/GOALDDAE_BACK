package com.goalddae.controller;

import com.goalddae.entity.CommunicationBoard;
import com.goalddae.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/board")
public class BoardController {

    BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }



    @RequestMapping({"/list/{page}", "/list"})
    public ResponseEntity<Map<String, Object>> list(@PathVariable(required = false) Integer page) {

        if (page == null) {
            page = 1;
        }

        final int PAGE_SIZE = 10;
        final int PAGE_BTN_NUM = 10;

        Page<CommunicationBoard> pageInfo = boardService.findAll(page, PAGE_SIZE);

        int currentPageNum = pageInfo.getNumber() + 1;
        int endPageNum = (int) Math.ceil(currentPageNum / (double) PAGE_BTN_NUM) * PAGE_BTN_NUM;
        int startPageNum = endPageNum - PAGE_BTN_NUM + 1;
        endPageNum = Math.min(endPageNum, pageInfo.getTotalPages());

        Map<String, Object> response = new HashMap<>();
        response.put("pageInfo", pageInfo);
        response.put("currentPageNum", currentPageNum);
        response.put("endPageNum", endPageNum);
        response.put("startPageNum", startPageNum);

        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/detail/{boardId}")
    public ResponseEntity<?> detail(@PathVariable long boardId){
        CommunicationBoard communicationBoard = boardService.findById(boardId);

        return ResponseEntity.ok(communicationBoard);
    }

}
