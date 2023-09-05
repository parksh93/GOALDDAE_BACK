package com.goalddae.dto.friend;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddFriendRequestDTO {
    private long fromUser;
    private long toUser;
}
