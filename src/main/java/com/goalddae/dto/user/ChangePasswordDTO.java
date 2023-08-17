package com.goalddae.dto.user;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ChangePasswordDTO {
    private String loginIdToken;
    private String password;
}
