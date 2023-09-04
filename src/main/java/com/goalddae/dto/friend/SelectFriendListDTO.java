package com.goalddae.dto.friend;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SelectFriendListDTO {
    private long userId;
    private String nickname;
}
