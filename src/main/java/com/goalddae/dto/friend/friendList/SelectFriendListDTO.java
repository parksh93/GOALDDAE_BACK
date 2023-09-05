package com.goalddae.dto.friend.friendList;

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
