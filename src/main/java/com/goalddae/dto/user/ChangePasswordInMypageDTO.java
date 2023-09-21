package com.goalddae.dto.user;

import com.goalddae.entity.User;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordInMypageDTO {
    private long id;
    private String oldPassword;
    private String newPassword;
}

