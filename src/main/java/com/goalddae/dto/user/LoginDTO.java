package com.goalddae.dto.user;

import com.goalddae.entity.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginDTO {
    private String loginId;
    private String password;

    public LoginDTO(User user){
        this.loginId = user.getLoginId();
        this.password = user.getPassword();
    }
}
