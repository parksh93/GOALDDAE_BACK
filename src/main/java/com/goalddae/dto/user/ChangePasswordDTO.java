package com.goalddae.dto.user;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class ChangePasswordDTO {
    private String loginId;
    private String password;
}
