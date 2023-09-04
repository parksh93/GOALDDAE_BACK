package com.goalddae.dto.friend;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FindFriendRequestDTO {
    private long userId;
}
