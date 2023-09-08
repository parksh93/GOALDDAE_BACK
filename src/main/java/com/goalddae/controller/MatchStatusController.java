package com.goalddae.controller;

import com.goalddae.dto.match.MatchStatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MatchStatusController {

    private final SimpMessagingTemplate template;

    @Autowired
    public MatchStatusController(SimpMessagingTemplate template) {
        this.template = template;
    }

    // 매치 상태가 변경될 때마다 호출됨
    public void notifyMatchStatusChange(Long matchId, String status) {
        this.template.convertAndSend("/topic/matchStatus", new MatchStatusDTO(matchId, status));
    }
}