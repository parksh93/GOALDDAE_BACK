package com.goalddae.dto.friend.friendAccept;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendRejectionDTO {
    private long userId;
    private long fromUser;
}
