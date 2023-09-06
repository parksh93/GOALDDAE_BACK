package com.goalddae.controller;

import com.goalddae.config.s3.S3Uploader;
import com.goalddae.dto.user.GetUserInfoDTO;
import com.goalddae.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

// 이미지 수정 컨트롤러
@RestController
public class S3Controller {
    private final S3Uploader s3Uploader;
    private final UserService userService;

    @Autowired
    public S3Controller(S3Uploader s3Uploader, UserService userService) {
        this.s3Uploader = s3Uploader;
        this.userService = userService;
    }

    @PostMapping("/user/profileImg")
    public ResponseEntity<String> updateUserImage(@RequestParam("file") MultipartFile multipartFile,
                                                  GetUserInfoDTO getUserInfoDTO) {
        try {
            // 오브젝트스토리지에 파일 저장
            s3Uploader.uploadFiles(multipartFile, "profile");
            // DB에 파일url 저장
            userService.updateProfileImg(getUserInfoDTO, multipartFile);

        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}