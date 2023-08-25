package com.goalddae.dto.friend;

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
