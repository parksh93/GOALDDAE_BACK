package com.goalddae.controller;

import com.goalddae.config.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class S3Controller {
    private final S3Uploader s3Uploader;

    @PostMapping("/{userId}/profileImg")
    public ResponseEntity<String> updateUserImage(@RequestParam("file") MultipartFile multipartFile) {
        try {
            s3Uploader.uploadFiles(multipartFile, "static");
        } catch (Exception e) {
            return ResponseEntity.ok("HttpStatus.BAD_REQUEST");
        } return ResponseEntity.ok("HttpStatus.NO_CONTENT");
    }
}
