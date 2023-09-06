package com.goalddae.dto.friend.friendList;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddFriendDTO {
    private long userId;
    private long friendId;
}
