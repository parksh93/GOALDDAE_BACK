package com.goalddae.dto.user;

import com.goalddae.entity.User;
import lombok.*;

@Getter
@Builder
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CheckLoginIdDTO {
    private String loginId;

    public CheckLoginIdDTO(User user){
        this.loginId = user.getLoginId();
    }
}
