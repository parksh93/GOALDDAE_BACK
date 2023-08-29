package com.goalddae.controller;

import com.goalddae.config.NcloudChatProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NcloudChatController {
    NcloudChatProperties ncloudChatProperties;
    @Autowired
    public NcloudChatController(NcloudChatProperties ncloudChatProperties){
        this.ncloudChatProperties = ncloudChatProperties;
    }

    @RequestMapping("/getChatProperties")
    public ResponseEntity<NcloudChatProperties> getChatProperties(){
        return ResponseEntity.ok(ncloudChatProperties);
    }

}
