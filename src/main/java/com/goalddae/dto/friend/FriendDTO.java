package com.goalddae.dto.friend;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendDTO {
    private long userId;
    private long friendId;
}
