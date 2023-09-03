package com.goalddae.dto.friend;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SelectFriendListDTO {
    private long userId;
    private String nickname;
}
