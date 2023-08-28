package com.goalddae.dto.user;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChangeProfileImgDTO {
    private String profileImgUrl;
    private MultipartFile imageFile;
}
