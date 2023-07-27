package com.goalddae.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class test {
    @GetMapping("/api/demo-web")
    public List<String> Hello(){
        return Arrays.asList("테스트 ", "몇번째야");
    }
}
