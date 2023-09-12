package com.goalddae.dto.friend.friendBlock;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnblockFriendDTO {
    private Long friendId;
    private Long userId;
}
