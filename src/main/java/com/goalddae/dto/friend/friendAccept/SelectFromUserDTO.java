package com.goalddae.dto.friend.friendAccept;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SelectFromUserDTO {
    private long fromUser;
    private long userId;
}
