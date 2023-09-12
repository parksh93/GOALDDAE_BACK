package com.goalddae.dto.friend.friendList;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FindFriendListResponseDTO {
    private String nickname;
    private String profileImgUrl;
    private long id;
}
