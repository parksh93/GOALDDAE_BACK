package com.goalddae.dto.friend.friendList;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SearchFriendDTO {
    private long id;
    private String nickname;
    private String profileImgUrl;
    private int friendAddCnt;
    private int friendAcceptCnt;
}
