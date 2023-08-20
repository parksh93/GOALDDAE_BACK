package com.goalddae.controller;

import com.goalddae.service.FriendService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api")
public class FriendController {

    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    // 친구목록 테이블 생성
    @PostMapping("/friend-list/create-table")
    public ResponseEntity<String> friendListCreateTable(@RequestParam("friendList") String friendList) {
        try {
            String decodedFriendList = URLDecoder.decode(friendList, StandardCharsets.UTF_8);
            friendService.createFriendListTable(decodedFriendList);
            return new ResponseEntity<>("테이블 생성 완료: " + decodedFriendList, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("테이블 생성 실패: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 친구신청 테이블 생성
    @PostMapping("/friend-add/create-table")
    public ResponseEntity<String> friendAddCreateTable(@RequestParam("friendAdd") String friendAdd) {
        try {
            String decodedFriendAdd = URLDecoder.decode(friendAdd, StandardCharsets.UTF_8);
            friendService.createFriendAddTable(decodedFriendAdd);
            return new ResponseEntity<>("테이블 생성 완료: " + decodedFriendAdd, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("테이블 생성 실패: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 친구수락 테이블 생성
    @PostMapping("/friend-accept/create-table")
    public ResponseEntity<String> friendAcceptCreateTable(@RequestParam("friendAccept") String friendAccept) {
        try {
            String decodedFriendAccept = URLDecoder.decode(friendAccept, StandardCharsets.UTF_8);
            friendService.createFriendAcceptTable(decodedFriendAccept);
            return new ResponseEntity<>("테이블 생성 완료: " + decodedFriendAccept, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("테이블 생성 실패: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 친구차단 테이블 생성
    @PostMapping("/friend-block/create-table")
    public ResponseEntity<String> friendBlockCreateTable(@RequestParam("friendBlock") String friendBlock) {
        try {
            String decodedFriendBlock = URLDecoder.decode(friendBlock, StandardCharsets.UTF_8);
            friendService.createFriendBlockTable(decodedFriendBlock);
            return new ResponseEntity<>("테이블 생성 완료: " + decodedFriendBlock, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("테이블 생성 실패: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
