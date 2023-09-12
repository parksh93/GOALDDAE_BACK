package com.goalddae.dto.friend.friendList;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FriendListDTO {
    private long id;
    private String friendId;
    private long userId;
}
