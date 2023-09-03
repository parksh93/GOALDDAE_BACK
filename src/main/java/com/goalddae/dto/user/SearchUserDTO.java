package com.goalddae.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SearchUserDTO {
    private String nickname;
    private String profileImgUrl;
}
