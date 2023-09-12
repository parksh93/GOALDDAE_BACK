package com.goalddae.dto.friend.friendAdd;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SelectToUserDTO {
    private long toUser;
    private long userId;
}
