package com.goalddae.dto.friend;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendStatusDTO {
    private long userId;
    private long status;
}
