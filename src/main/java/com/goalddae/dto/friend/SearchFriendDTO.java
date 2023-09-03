package com.goalddae.dto.friend;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SearchFriendDTO {
    private long id;
    private String nickname;
    private String profileImgUrl;
}
